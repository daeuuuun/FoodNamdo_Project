from fastapi import FastAPI, File, UploadFile, HTTPException, Query, Response
from contextlib import asynccontextmanager
import chromadb_utils, chromadb, os, SortBy, Category, Region, plyvel, leveldb_utils, json, Table, recommend_utils, time, threading
from chromadb.utils.embedding_functions import OpenCLIPEmbeddingFunction
from chromadb.utils.data_loaders import ImageLoader
from fastapi.middleware.cors import CORSMiddleware

# ChromaDB 정보 존재 여부 확인
chromaDB_exists = os.path.exists("./chromadb_data/")

# ChromaDB 정보 생성 혹은 가져오기
client = chromadb.PersistentClient(path="./chromadb_data/")
embedding_function = OpenCLIPEmbeddingFunction()
image_loader = ImageLoader()
rstr_img_collection = client.get_or_create_collection(
    name='rstr_img_collection',
    embedding_function=embedding_function,
    data_loader=image_loader)
review_img_collection = client.get_or_create_collection(
    name='review_img_collection',
    embedding_function=embedding_function,
    data_loader=image_loader)

#LevelDB 생성 혹은 가져오기
levelDB = plyvel.DB("./levelDB_data/", create_if_missing=True)

# 추천 모델
model = None
review_data = None
UPDATE_INTERVAL = 3600  # 1시간마다 업데이트
# 종료 조건을 위한 전역 변수
terminate_thread = False

def update_model():
    global terminate_thread, model, review_data
    while not terminate_thread:
        model, review_data = recommend_utils.init()
        time.sleep(UPDATE_INTERVAL)

update_thread = threading.Thread(target=update_model)
update_thread.start()

@asynccontextmanager
async def lifespan(app: FastAPI):
    global terminate_thread
    if not chromaDB_exists:
        chromadb_utils.init(rstr_img_collection, "rstr_img")
        chromadb_utils.init(review_img_collection, "review_img")
    yield
    terminate_thread = True
    update_thread.join()
    del model
    levelDB.close()
    print("FastAPI application is shutting down!")

app = FastAPI(lifespan=lifespan)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 출처 허용
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"]   # 모든 헤더 허용
)

# 허용 확장자
ALLOWED_IMAGE_EXTENSIONS = {'png', 'jpg', 'jpeg'}
def is_valid_image_filename(filename: str) -> bool:
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_IMAGE_EXTENSIONS
error_422_detail = "Only images with extensions {} are allowed.".format(ALLOWED_IMAGE_EXTENSIONS)
image_search_description = "이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 유사도를 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다.(sort_order: 정렬 기준, reverse: False라면 오름차순, True라면 내림차순, category: 해당 카테고리만 필터링해서 검색, region: 해당 지역만 필터링해서 검색, table: 음식점 이미지 혹은 리뷰 이미지 혹은 전체 이미지 검색 여부 선택)"
@app.post("/image_to_image/",
          summary="음식점 이미지 검색과 리뷰 이미지 검색 혹은 전체 음식점 이미지와 리뷰 이미지를 이용한 통합 이미지 검색",
          description=image_search_description)
async def image_search_all(file: UploadFile = File(..., description="이미지 파일 업로드"), 
                           similarity: float = Query(..., ge=0,  description="유사도 0에 근접할수록 유사도 증가 0에 멀어질수록 유사도 감소"), 
                           n_results: int = Query(30, ge=1, le=70, description="음식점 혹은 리뷰 이미지 최대 검색 수"), 
                           page_size: int = Query(8, ge=1, description="페이지네이션의 1페이지 당 크기"), 
                           page_number: int = Query(1, ge=1, description="얻고자하는 페이지 번호"), 
                           sort_order: SortBy.Column = SortBy.Column.distance, 
                           reverse: bool = False, 
                           category: Category.rstrCategory = Category.rstrCategory.전체, 
                           region: Region.rstrRegion = Region.rstrRegion.전체, 
                           table: Table.searchTable = Table.searchTable.전체):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    try:
        results = chromadb_utils.img_to_img(rstr_img_collection, review_img_collection, file, file.filename.split(".")[-1], similarity, n_results, table)
    except Exception as e:
        print(e)
        raise HTTPException(status_code=422, detail="This file cannot be used")
    random = leveldb_utils.insert_results(levelDB, results)
    return sort_paginate_json(results, page_size, page_number, sort_order, reverse, category, region, random)

@app.get("/{random}/")
async def image_search_result(random: str, page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False, category: Category.rstrCategory = Category.rstrCategory.전체, region: Region.rstrRegion = Region.rstrRegion.전체):
    result = levelDB.get(random.encode())
    if result is None:
        raise HTTPException(status_code=404, detail="Page not found")
    
    return sort_paginate_json(json.loads(result.decode()), page_size, page_number, sort_order, reverse, category, region, random)

def sort_paginate_json(json_data, page_size, page_number, sort_order, reverse, category, region, random):
    json_data = sorted(json_data, key=lambda x: x[sort_order.value], reverse=reverse)

    json_data = remove_final(json_data, category, region)
    total_pages = len(json_data) // page_size + (1 if len(json_data) % page_size != 0 else 0)

    if page_number < 1 or page_number > total_pages:
        raise HTTPException(status_code=404, detail={"message": "Invalid page number. Please choose a page within the range.", "page_size": page_size, "total_pages": total_pages})
    
    start_index = (page_number - 1) * page_size
    end_index = min(start_index + page_size, len(json_data))
    result = {"random": random, "page_size": page_size, "total_pages": total_pages, "total_rstr": len(json_data)}

    # e표기법에서 소수점으로 표기
    # for item in json_data:
    #     item['distance'] = format(float(item['distance']), '.17f')
    result['rstr'] = json_data[start_index:end_index]
    return result

# 카테고리, 지역 필터링
def remove_final(query_result, category: Category.rstrCategory, region: Region.rstrRegion):
    if category != Category.rstrCategory.전체:
        query_result = [item for item in query_result if item["category_name"] == category.value]
    if region != Region.rstrRegion.전체:
        query_result = [item for item in query_result if item["rstr_region"] == region.value]
    return query_result

@app.get("/recommend/{user_id}")
async def recommend(user_id: int, num_recommendations: int = Query(5, ge=1, le=10)):
    global model, review_data
    return recommend_utils.rstr(user_id, num_recommendations, model, review_data)

@app.post('/review_image/{review_img_id}')
async def insert_review_image(review_img_id: int):
    chromadb_utils.insert_chromaDB_review_image(review_img_collection, review_img_id)
    return Response(status_code=200)

@app.delete('/review_image/{review_img_id}')
async def delete_review_image(review_img_id):
    chromadb_utils.delete_review_image(review_img_collection, review_img_id)
    return Response(status_code=200)