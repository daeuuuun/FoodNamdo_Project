from fastapi import FastAPI, File, UploadFile, HTTPException, Form
import chromadb_utils
import chromadb
from chromadb.utils.embedding_functions import OpenCLIPEmbeddingFunction
from chromadb.utils.data_loaders import ImageLoader
from fastapi.middleware.cors import CORSMiddleware
import os

# ChromaDB 정보 존재 여부 확인
chromaDB_exists = os.path.exists("./chromadb_data/")

# ChromaDB 정보 생성 혹은 가져오기
client = chromadb.PersistentClient(path="./chromadb_data/")
embedding_function = OpenCLIPEmbeddingFunction()
image_loader = ImageLoader()
collection = client.get_or_create_collection(
    name='multimodal_collection',
    embedding_function=embedding_function,
    data_loader=image_loader)

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 출처 허용
    allow_credentials=True,
    allow_methods=["*"],  # 모든 HTTP 메서드 허용
    allow_headers=["*"]   # 모든 헤더 허용
)

ALLOWED_IMAGE_EXTENSIONS = {'png', 'jpg', 'jpeg'}

def is_valid_image_filename(filename: str) -> bool:
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_IMAGE_EXTENSIONS

@app.post("/image_to_image/",
          summary="이미지를 이용한 이미지 검색",
          description="이미지 파일(png, jpg, jpeg)와 결과 수(n_results)의 값과 함께 요청하면 입력한 이미지 파일과 유사한 이미지를 입력한 결과 수만큼 검색한다.")
async def create_upload_file(file: UploadFile = File(...), n_results: int = Form(...)):
    # 파일이 이미지인지 확인
    if not is_valid_image_filename(file.filename):
        raise HTTPException(status_code=400, detail="Only images with extensions {} are allowed.".format(ALLOWED_IMAGE_EXTENSIONS))
    
    results = chromadb_utils.img_to_img(collection, file, file.filename.split(".")[-1], n_results)
    return results

@app.get("/")
def read_root():
    return {"Hello" : "World"}

@app.on_event("startup")
async def on_startup():
    if not chromaDB_exists:
        chromadb_utils.init(collection)

@app.on_event("shutdown")
async def on_shutdown():
    print("FastAPI application is shutting down!")