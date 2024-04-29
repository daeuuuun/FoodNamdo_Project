FROM python:3.10

RUN pip3 install chromadb open-clip-torch fastapi uvicorn[standard] mariadb scikit-surprise tqdm python-multipart uuid

WORKDIR /usr/src/app

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]