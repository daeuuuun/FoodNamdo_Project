# 창의융합종합설계2 - FoodNamdo

## 설치 방법
1. 터미널 창에서 원하는 폴더 목록으로 이동 후 `git clone https://github.com/foodnamdo/project.git`
2. [해당 링크](https://drive.google.com/file/d/1poxUZRfiwbtgnQqPnXXoEj0zHD5DSpiB/view?usp=sharing)에서 파일을 받은 후 `project`폴더 안에 압축해제 한다.
3. .env 파일을 추가하여 내용을 채운다. ([참고](https://github.com/foodnamdo/env))
4. [프로젝트 구조도](#프로젝트-구조도)와 같이 구성해야 한다.
5. `docker-compose up --build` 명령어로 프로젝트 실행

### 프로젝트 구조도
```
FoodNamdo(Directory)
└─backend
    └─FoodNamdo
    └─imageSearchRecommend
        └─chromadb_data
            └─chromad.sqlite3
└─frontend
└─.env
└─docker-copmose.yml
```