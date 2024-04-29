import os
import mariadb
from mariadb import Error

db_config = {
    'host': os.getenv("MYSQL_USER"),
    'user': os.getenv("MYSQL_USER"),
    'password': os.getenv("MYSQL_PASSWORD"),
    'database': os.getenv("MYSQL_DATABASE"),
    'port': os.getenv("MYSQL_PORT")
}

# DB 연결
def connect_to_mariadb():
    try:
        connection= mariadb.connect(**db_config)
        return connection
    except mariadb.Error as e:
        print(f"Error connecting to MariaDB Platform: {e}")

# 데이터 조회 함수 정의
def fetch_data_from_table(connection, table):
    try:
        # 커서 생성
        cursor = connection.cursor()

        # 데이터 조회 쿼리 실행
        query = f"SELECT * FROM {table}"
        cursor.execute(query)

        # 결과 가져오기
        records = cursor.fetchall()
        return records

    except Error as e:
        print(f"Error fetching data from MariaDB: {e}")
        return None
    finally:
        # 커서 닫기
        cursor.close()

def select_from_table(db_connection, table):
    data = fetch_data_from_table(db_connection, table)
    return data

def select_from_db():
    # 연결 및 데이터 조회
    db_connection = connect_to_mariadb()
    rstrimg = select_from_table(db_connection, "RstrImg")
    db_connection.close()
    return rstrimg
    