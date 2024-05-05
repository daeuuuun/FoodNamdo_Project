from fastapi import FastAPI, File, UploadFile, HTTPException, Query
from contextlib import asynccontextmanager
import chromadb_utils, chromadb, os, copy, SortBy, Category, Region, plyvel, leveldb_utils, json
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

@asynccontextmanager
async def lifespan(app: FastAPI):
    if not chromaDB_exists:
        chromadb_utils.init(rstr_img_collection, "rstr_img")
        chromadb_utils.init(review_img_collection, "review_img")
    yield
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
image_search_description = "이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 유사도(similarity: 0에 근접할수록 유사도 증가 0에 멀어질수록 유사도 감소) 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다."
@app.post("/image_to_image/",
          summary="음식점 이미지와 리뷰 이미지를 이용한 이미지 검색",
          description=image_search_description)
async def image_search_all(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False, category: Category.rstrCategory = Category.rstrCategory.전체, region: Region.rstrRegion = Region.rstrRegion.전체):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "rstr_img")
    results_review_img = None
    results = None
    try:
        file_copy = copy.deepcopy(file)
        results_review_img = chromadb_utils.img_to_img(review_img_collection, file_copy, file.filename.split(".")[-1], similarity, n_results, "review_img")
    except Exception as e:
        print("Error copying image data:", e)
    if results_review_img is not None:
        results = results_rstr_img + results_review_img
    else:
        results = results_rstr_img
    random = leveldb_utils.insert_results(levelDB, results)
    return sort_paginate_json(results, page_size, page_number, sort_order, reverse, category, region, random)

@app.post("/image_to_image_rstr/",
          summary="음식점 이미지를 이용한 이미지 검색",
          description=image_search_description)
async def image_search_only_rstr(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False, category: Category.rstrCategory = Category.rstrCategory.전체, region: Region.rstrRegion = Region.rstrRegion.전체):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "rstr_img")
    random = leveldb_utils.insert_results(levelDB, results_rstr_img)
    return sort_paginate_json(results_rstr_img, page_size, page_number, sort_order, reverse, category, region, random)

@app.post("/img_to_img_review/",
          summary="리뷰 이미지를 이용한 이미지 검색",
          description=image_search_description)
async def image_search_only_review(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False, category: Category.rstrCategory = Category.rstrCategory.전체, region: Region.rstrRegion = Region.rstrRegion.전체):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    results_review_img = chromadb_utils.img_to_img(review_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "review_img")
    random = leveldb_utils.insert_results(levelDB, results_review_img)
    return sort_paginate_json(results_review_img, page_size, page_number, sort_order, reverse, category, region, random)

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