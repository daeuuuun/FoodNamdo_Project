FROM python:3.10

RUN apt-get update && apt-get install -y libleveldb-dev

RUN pip3 install chromadb open-clip-torch fastapi uvicorn[standard] mariadb scikit-surprise tqdm python-multipart uuid plyvel pandas

WORKDIR /usr/src/app

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]