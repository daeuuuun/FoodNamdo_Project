import mariadb_utils
import os
from tqdm import tqdm
import uuid
import main
import time
def init(collection):
    table = "rstr_img"
    data = mariadb_utils.select_from_db_data(table)
    columns = mariadb_utils.select_from_db_column(table)
    save_path = "./img/"
    
    img_saving(collection, save_path, table, data, columns)

def img_saving(collection, save_path, table, data, columns):
    os.makedirs(save_path + table, exist_ok=True)
    for attribute in tqdm(data, desc="Processing", unit="item"):
        file_extension_name = str(attribute[2]).split(".")[-1]
        if not main.is_valid_image_filename(str(attribute[2])):
            params = str(attribute[2]).split('?')[1]
            param_list = params.split('&')
            file_extension_name = [param for param in param_list if param.startswith('image_name=')][0].split('.')[-1]

        local_save_path = save_path + table + "/" + str(attribute[0]) + "." + file_extension_name

        if not os.path.exists(local_save_path):
            os.system('curl "' + str(attribute[2]) + '" > ' + local_save_path)
        metadata = {columns[0]: str(attribute[0]), columns[1]: str(attribute[1]), columns[2]: str(attribute[2]), "table": table}

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

keys_to_remove = ["embeddings", "documents", "uris", "data"]

# 이미지로 음식점 이미지 유사도 검색
def img_to_img(collection, file, file_extension_name, n_results):
    save_path = "./img/temp/"
    os.makedirs(save_path, exist_ok=True)
    unique_filename = f"{uuid.uuid4()}.{file_extension_name}"

    url = save_path + unique_filename
    with open(url, "wb") as f:
        f.write(file.file.read())
    query_result = collection.query(query_uris=url, n_results = n_results)
    
    for key in keys_to_remove:
        query_result.pop(key, None)
    os.remove(url)
    return query_result