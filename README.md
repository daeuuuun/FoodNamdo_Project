# 창의융합종합설계2 - FoodNamdo

### 설치 방법
1. 터미널 창에서 원하는 폴더 목록으로 이동 후 `git clone https://github.com/foodnamdo/project.git`
2. [해당 링크](https://drive.google.com/drive/folders/1xjtk-dxrYdnLTl64YjEbaxXenkWAJVn6?usp=drive_link)에서 파일을 받은 후 `project/backend/imageSearchRecommend`디렉토리 안에 압축해제 한다.(해당 방법을 권장)
    - 파일을 다운받지 않더라도 자동으로 생성되어 최신 이미지 서버 반영 가능(예상 소요 시간 40분, 권장하지 않음)
3. .env 파일을 추가하여 내용을 채운다. ([참고](https://github.com/foodnamdo/env))
4. [프로젝트 구조도](#프로젝트-구조도)와 같이 구성해야 한다.

### 프로젝트 구조도
```
project(Directory)
└─backend
    └─FoodNamdo
    └─imageSearchRecommend
        └─chromadb_data (압축 해제된 폴더)
            └─chroma.sqlite3
            └─...
└─frontend
└─.env
└─docker-copmose.yml
```
