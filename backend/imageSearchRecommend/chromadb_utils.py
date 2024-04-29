import mariadb_utils
import os
from tqdm import tqdm
import uuid

def init(collection):
    data = mariadb_utils.select_from_db()

    save_path = "./img/"
    table = "rstrimg"

    os.makedirs(save_path + table, exist_ok=True)
    for attribute in tqdm(data, desc="Processing", unit="item"):
        local_save_path = save_path + table + "/" + str(attribute[0]) + "." + str(attribute[2]).split(".")[-1]
        if not os.path.exists(local_save_path):
            os.system("curl " + str(attribute[2]) + " > " + local_save_path)
        metadata = {"rstr_img_id": str(attribute[0]), "rstr_id": str(attribute[1]), "rstr_img_url": str(attribute[2]), "table": "rstrimg", "local_path": local_save_path}

        collection.add(
            ids=str(attribute[0]),
            uris=local_save_path,
            metadatas=metadata
        )

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