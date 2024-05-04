from fastapi import FastAPI, File, UploadFile, HTTPException, Form, Query
from contextlib import asynccontextmanager
import chromadb_utils
import chromadb
from chromadb.utils.embedding_functions import OpenCLIPEmbeddingFunction
from chromadb.utils.data_loaders import ImageLoader
from fastapi.middleware.cors import CORSMiddleware
import os
import copy
import SortBy

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

@asynccontextmanager
async def lifespan(app: FastAPI):
    if not chromaDB_exists:
        chromadb_utils.init(rstr_img_collection, "rstr_img")
        chromadb_utils.init(review_img_collection, "review_img")
    yield
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
async def image_search_all(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    file_copy = copy.deepcopy(file)
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "rstr_img")
    results_review_img = chromadb_utils.img_to_img(review_img_collection, file_copy, file.filename.split(".")[-1], similarity, n_results, "review_img")
    results = results_rstr_img + results_review_img
    return sort_paginate_json(results, page_size, page_number, sort_order, reverse)

@app.post("/image_to_image_rstr/",
          summary="음식점 이미지를 이용한 이미지 검색",
          description=image_search_description)
async def image_search_only_rstr(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "rstr_img")
    return sort_paginate_json(results_rstr_img, page_size, page_number, sort_order, reverse)

@app.post("/img_to_img_review/",
          summary="리뷰 이미지를 이용한 이미지 검색",
          description=image_search_description)
async def image_search_only_review(file: UploadFile = File(...), similarity: float = Query(..., ge=0), n_results: int = Query(30, ge=1, le=70), page_size: int = Query(8, ge=1), page_number: int = Query(1, ge=1), sort_order: SortBy.Column = SortBy.Column.distance, reverse: bool = False):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=422, detail=error_422_detail)
    
    results_review_img = chromadb_utils.img_to_img(review_img_collection, file, file.filename.split(".")[-1], similarity, n_results, "review_img")
    return sort_paginate_json(results_review_img, page_size, page_number, sort_order, reverse)

def sort_paginate_json(json_data, page_size, page_number, sort_order, reverse):
    json_data = sorted(json_data, key=lambda x: x[sort_order.value], reverse=reverse)
    total_pages = len(json_data) // page_size + (1 if len(json_data) % page_size != 0 else 0)
    
    if page_number < 1 or page_number > total_pages:
        raise HTTPException(status_code=404, detail={"message": "Invalid page number. Please choose a page within the range.", "page_size": page_size, "total_pages": total_pages})
    
    start_index = (page_number - 1) * page_size
    end_index = min(start_index + page_size, len(json_data))
    result = {"page_size": page_size, "total_pages": total_pages}
    result['rstr'] = json_data[start_index:end_index]
    return result