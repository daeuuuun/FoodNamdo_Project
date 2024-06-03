import mariadb_utils, os, uuid, main, Table
from tqdm import tqdm
from fastapi import HTTPException
import urllib.parse

def init(collection, table):
    query = ""
    if(table == "rstr_img"):
        query = f"SELECT * FROM {table}"
    elif(table == "review_img"):
        query = f"SELECT review_img_id, {table}.review_id as review_id, review_img_url, rstr_id FROM {table} JOIN review ON {table}.review_id = review.review_id"
    save_path = "./img/"
    data = mariadb_utils.select_from_db_data(query)
    columns = mariadb_utils.select_from_db_column(query)
    img_saving(collection, save_path, table, data, columns)

# 이미지 검색을 위한 이미지 저장 및 ChromaDB 저장
def img_saving(collection, save_path, table, data, columns):
    os.makedirs(save_path + table, exist_ok=True)
    for attribute in tqdm(data, desc="Processing", unit="item"):
        file_extension_name = str(attribute[2]).split(".")[-1]
        if not main.is_valid_image_filename(str(attribute[2])):
            params = str(attribute[2]).split('?')[1]
            param_list = params.split('&')
            file_extension_name = [param for param in param_list if param.startswith('image_name=')][0].split('.')[-1]

        local_save_path = save_path + table + "/" + str(attribute[0]) + "." + file_extension_name

        os.system('curl "' + str(attribute[2]) + '" > ' + local_save_path)      # 로컬에 이미지 저장

        # 메타 데이터 저장
        metadata = {"table": table}
        for i in range(len(columns)):
            metadata[columns[i]] = str(attribute[i])

        try :
            collection.add(
                ids=str(attribute[0]),
                uris=local_save_path,
                metadatas=metadata
            )
        except Exception as e:
            print("이미지 저장 도중 오류 발생: ", e)
            print("이미지 오류 table: ", table)
            print("이미지 오류 id: ", str(attribute[0]))
            raise HTTPException(status_code=422, detail="This file is not stored")

keys_to_remove = ["embeddings", "documents", "uris", "data"]

# 이미지로 음식점 이미지 유사도 검색
def img_to_img(rstr_img_collection, review_img_collection, file, file_extension_name, similarity, n_results, table):
    save_path = "./img/temp/"
    os.makedirs(save_path, exist_ok=True)
    unique_filename = f"{uuid.uuid4()}.{file_extension_name}"

    url = save_path + unique_filename
    with open(url, "wb") as f:
        f.write(file.file.read())

    query_result = None
    if table == Table.searchTable.음식점_이미지:
        query_result = image_search(rstr_img_collection, url, n_results, similarity, table)
    elif table == Table.searchTable.리뷰_이미지:
        if n_results > review_img_collection.count():
            n_results = review_img_collection.count()
        query_result = image_search(review_img_collection, url, n_results, similarity, table)
    else:
        query_result = image_search(rstr_img_collection, url, n_results, similarity, Table.searchTable.음식점_이미지)
        if n_results > review_img_collection.count():
            n_results = review_img_collection.count()
        query_result += image_search(review_img_collection, url, n_results, similarity, Table.searchTable.리뷰_이미지)
    
    os.remove(url)
    return query_result

def image_search(collection, url, n_results, similarity, table):
    query_result = collection.query(query_uris=url, n_results=n_results)
    
    for key in keys_to_remove:
        query_result.pop(key, None)

    # distances 값을 metadata에 넣기
    for i in range(len(query_result['metadatas'][0])):
        query_result['metadatas'][0][i]['distance'] = float(query_result['distances'][0][i])
    return filter_by_condition(query_result, similarity, table)

# 결과 필터링
def filter_by_condition(query_result, similarity, table):
    query_result = remove_above_threshold(remove_duplicates(query_result), similarity)
    if table == Table.searchTable.음식점_이미지:
        query_result = insert_rstrimg_rstr_info(query_result)
    elif table == Table.searchTable.리뷰_이미지:
        query_result = insert_reviewimg_rstr_info(query_result)
    return query_result

# 중복 제거
def remove_duplicates(query_result):
    # 중복을 제거한 rstr_id 값들을 저장할 set 생성
    unique_rstr_ids = set()

    # 중복을 제거한 metadata 생성
    unique_metadata = []

    # metadata에서 중복을 제거하고 unique_rstr_ids에 추가
    for metadata in query_result['metadatas'][0]:
        rstr_id = metadata['rstr_id']
        if rstr_id not in unique_rstr_ids:
            metadata['rstr_id'] = int(metadata['rstr_id'])
            unique_metadata.append(metadata)
            unique_rstr_ids.add(rstr_id)

    # 중복이 제거된 metadata를 원래 데이터에 적용
    return unique_metadata

# 유사도 필터링
def remove_above_threshold(query_result, similarity):
    return [item for item in query_result if item["distance"] <= similarity]

rstr_column_array = ['example', 'relax', 'rstr_name', 'rstr_region', 'category_name', 'rstr_review_rating', "rstr_review_count", "rstr_favorite_count"]
rstr_column = ", ".join(rstr_column_array)
join_category = "JOIN rstr_category ON rstr_category.rstr_id = rstr.rstr_id JOIN category ON category.category_id = rstr_category.category_id"
def insert_rstrimg_rstr_info(query_result):
    for item in query_result:
        query = f"SELECT {rstr_column} FROM rstr_img JOIN rstr ON rstr_img.rstr_id = rstr.rstr_id {join_category} WHERE rstr_img.rstr_id = {item['rstr_id']}"
        data = mariadb_utils.select_from_db_data(query)
        for i in range(len(rstr_column_array)):
            item[rstr_column_array[i]] = data[0][i]
    return query_result

def insert_reviewimg_rstr_info(query_result):
    for item in query_result:
        query = f"SELECT {rstr_column} FROM review_img JOIN review ON review_img.review_id = review.review_id JOIN rstr ON review.rstr_id = rstr.rstr_id {join_category} WHERE review_img.review_id = {item['review_id']}"
        data = mariadb_utils.select_from_db_data(query)
        for i in range(len(rstr_column_array)):
            item[rstr_column_array[i]] = data[0][i]
    return query_result

def insert_chromaDB_review_image(collection, review_img_id):
    table = "review_img"
    query = f"SELECT review_img_id, {table}.review_id as review_id, review_img_url, rstr_id FROM {table} JOIN review ON {table}.review_id = review.review_id WHERE {table}.review_img_id = {review_img_id}"
    save_path = "./img/"
    data = mariadb_utils.select_from_db_data(query)
    columns = mariadb_utils.select_from_db_column(query)
    img_saving(collection, save_path, table, data, columns)

def delete_review_image(collection, review_img_id):
    collection.delete(ids=[str(review_img_id)])