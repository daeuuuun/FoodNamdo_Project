# 창의융합종합설계2 - FoodNamdo

### 설치 방법
1. 터미널 창에서 원하는 폴더 목록으로 이동 후 `git clone https://github.com/foodnamdo/project.git`
2. [해당 링크](https://drive.google.com/file/d/1poxUZRfiwbtgnQqPnXXoEj0zHD5DSpiB/view?usp=sharing)에서 파일을 받은 후 `project`폴더 안에 압축해제 한다. (추가하지 않더라도 자동으로 생성되어 최신 이미지 서버 반영 가능, 예상 소요 시간 40분)
3. .env 파일을 추가하여 내용을 채운다. ([참고](https://github.com/foodnamdo/env))
4. [프로젝트 구조도](#프로젝트-구조도)와 같이 구성해야 한다.
5. backend/FoodNamdo 디렉토리에서 아래의 명령어 각각 실행
    ./gradlew build
    java -jar build/libs/FoodNamdo-0.0.1-SNAPSHOT.jar
6. project 디렉토리에서 `docker-compose up --build` 명령어로 프로젝트 실행

### 프로젝트 구조도
```
FoodNamdo(Directory)
└─backend
    └─FoodNamdo
    └─imageSearchRecommend
        └─chromadb_data
            └─chroma.sqlite3
            └─...
└─frontend
└─.env
└─docker-copmose.yml
```
