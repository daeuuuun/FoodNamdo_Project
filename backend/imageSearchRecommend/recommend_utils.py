from surprise import Dataset, Reader, KNNBasic
import pandas as pd
import mariadb_utils

reader = Reader(rating_scale=(0, 5))

def init():
    # 데이터베이스에서 데이터 가져와 Surprise 데이터셋으로 변환
    query = "SELECT user_id, rstr_id, rating FROM review"
    review_data = mariadb_utils.select_from_db_data(query)
    review_data = pd.DataFrame(review_data, columns=['user_id', 'rstr_id', 'rating'])
    surprise_data = Dataset.load_from_df(review_data[['user_id', 'rstr_id', 'rating']], reader)
    surprise_data = surprise_data.build_full_trainset()
    # # KNN 기반의 협업 필터링 모델 학습
    model = KNNBasic(sim_options={'name': 'cosine', 'user_based': True})
    model.fit(surprise_data)
    return model, review_data

def rstr(user_id, num_recommendations, model, review_data):
    restaurants_to_predict = review_data.loc[~review_data['rstr_id'].isin(review_data[review_data['user_id'] == user_id]['rstr_id'])]['rstr_id'].unique()
    # 추천 예측
    predictions = [model.predict(user_id, rstr_id) for rstr_id in restaurants_to_predict]
    top_recommendations = sorted(predictions, key=lambda x: x.est, reverse=True)[:num_recommendations]

    result = {"rstr": []}
    for prediction in top_recommendations:
        item = {"rstr_id" : int(prediction.iid), "predicted_rating" : float(prediction.est), "rstr_img_url": []}
        result['rstr'].append(insert_all_rstr_rstrimg(prediction.iid, item))
    return result

rstr_column_array = ['example', 'relax', 'rstr_name', 'rstr_region', 'category_name', 'rstr_review_rating', "rstr_review_count"]
rstr_column = ", ".join(rstr_column_array)
join_category = "JOIN rstr_category ON rstr_category.rstr_id = rstr.rstr_id JOIN category ON category.category_id = rstr_category.category_id"
def insert_all_rstr_rstrimg(rstr_id, item):
    query = f"SELECT {rstr_column} FROM rstr {join_category} WHERE rstr.rstr_id = {rstr_id}"
    data = mariadb_utils.select_from_db_data(query)
    for i in range(len(rstr_column_array)):
        item[rstr_column_array[i]] = data[0][i]
    return insert_rstrimg(rstr_id, item)

def insert_rstrimg(rstr_id, item):
    query = f"SELECT rstr_img_url FROM rstr_img WHERE rstr_img.rstr_id = {rstr_id}"
    data = mariadb_utils.select_from_db_data(query)
    for i in range(len(data)):
        item['rstr_img_url'].append(data[i][0])
    return item
