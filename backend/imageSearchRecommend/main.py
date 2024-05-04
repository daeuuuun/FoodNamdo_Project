from fastapi import FastAPI, File, UploadFile, HTTPException, Form, Query
from contextlib import asynccontextmanager
import chromadb_utils
import chromadb
from chromadb.utils.embedding_functions import OpenCLIPEmbeddingFunction
from chromadb.utils.data_loaders import ImageLoader
from fastapi.middleware.cors import CORSMiddleware
import os
import copy

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

@app.post("/image_to_image/",
          summary="음식점 이미지와 리뷰 이미지를 이용한 이미지 검색",
          description="이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 유사도(similarity: 0에 근접할수록 유사도 증가 0에 멀어질수록 유사도 감소) 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다.")
async def image_search_all(file: UploadFile = File(...), n_results: int = Form(...), similarity: float = Query(..., ge=0, le=10)):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=400, detail="Only images with extensions {} are allowed.".format(ALLOWED_IMAGE_EXTENSIONS))
    
    file_copy = copy.deepcopy(file)
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, "rstr_img")
    results_review_img = chromadb_utils.img_to_img(review_img_collection, file_copy, file.filename.split(".")[-1], similarity, "review_img")
    return sorted(results_rstr_img + results_review_img, key=lambda x: x['distance'])[:n_results]

@app.post("/image_to_image_rstr/",
          summary="음식점 이미지를 이용한 이미지 검색",
          description="이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 유사도(similarity: 0에 근접할수록 유사도 증가 0에 멀어질수록 유사도 감소) 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다.")
async def image_search_only_rstr(file: UploadFile = File(...), n_results: int = Form(...), similarity: float = Query(..., ge=0, le=10)):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=400, detail="Only images with extensions {} are allowed.".format(ALLOWED_IMAGE_EXTENSIONS))
    
    results_rstr_img = chromadb_utils.img_to_img(rstr_img_collection, file, file.filename.split(".")[-1], similarity, "rstr_img")
    return results_rstr_img[:n_results]

@app.post("/img_to_img_review/",
          summary="리뷰 이미지를 이용한 이미지 검색",
          description="이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 유사도(similarity: 0에 근접할수록 유사도 증가 0에 멀어질수록 유사도 감소) 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다.")
async def image_search_only_review(file: UploadFile = File(...), n_results: int = Form(...), similarity: float = Query(..., ge=0, le=10)):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=400, detail="Only images with extensions {} are allowed.".format(ALLOWED_IMAGE_EXTENSIONS))
    
    results_review_img = chromadb_utils.img_to_img(review_img_collection, file, file.filename.split(".")[-1], similarity, "review_img")
    return results_review_img[:n_results]