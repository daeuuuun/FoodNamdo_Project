import mariadbRepository as mariadbRepository

rstr_column_array = ['rstr.rstr_id as rstr_id', 'example', 'relax', 'rstr_name', 'rstr_region', 'category_name', 'rstr_review_rating', 'rstr_review_count']
join_category = "JOIN rstr_category ON rstr_category.rstr_id = rstr.rstr_id JOIN category ON category.category_id = rstr_category.category_id"
def rstr(user_id, n_results):
    query = f"SELECT last_visit FROM user WHERE user_id = {user_id}"
    data = mariadbRepository.select_from_db_data(query)
    rstr_id = data[0][0]

    
    if rstr_id < 0:
        query = "SELECT rstr_id, rstr_la, rstr_lo FROM rstr ORDER BY rstr_naver_rating DESC, RAND() LIMIT 1"
        data = mariadbRepository.select_from_db_data(query)
        rstr_id = data[0][0]
    
    query = f"SELECT rstr_la, rstr_lo, rstr_name FROM rstr WHERE rstr.rstr_id = {rstr_id}"
    data = mariadbRepository.select_from_db_data(query)
    rstr_la = data[0][0]
    rstr_lo = data[0][1]
    rstr_name = data[0][2]

    query_base = f"""
        SELECT {','.join(rstr_column_array)}
        FROM rstr
        {join_category}
        WHERE NOT EXISTS (
            SELECT *
            FROM review
            WHERE review.rstr_id = rstr.rstr_id
            AND review.user_id = {user_id}
        )
    """
    
    result = {
        'rstr_id': rstr_id, 'rstr_name': rstr_name,
        'region': select_data(query_base, n_results, rstr_id, 'rstr_region'),
        'category': select_data(query_base, n_results, rstr_id, 'category_name'),
        'example': select_data(query_base, n_results, rstr_id, 'example'),
        'relax': select_data(query_base, n_results, rstr_id, 'relax'),
        'nearby': select_nearby(n_results, rstr_la, rstr_lo)
    }
    return result

rstr_column_list = [
    'rstr_id', 'example', 'relax', 'rstr_name', 
    'rstr_region', 'category_name', 'rstr_review_rating', 'rstr_review_count'
]

def select_data(base_query, n_results, rstr_id, column_name):
    sub_query = f"(SELECT {column_name} FROM rstr {join_category if column_name == 'category_name' else ''} WHERE rstr.rstr_id = {rstr_id})"
    query = f"{base_query} AND {column_name} = {sub_query} ORDER BY rstr_naver_rating DESC, RAND() LIMIT {n_results}"
    return info_to_json(query, rstr_column_list)

def info_to_json(query, column_list):
    data = mariadbRepository.select_from_db_data(query)
    items = []
    for row in data:
        item = {}
        for idx, column_name in enumerate(column_list):
            item[column_name] = row[idx]
        item = insert_rstrimg(item)
        items.append(item)
    return items

nearby_column_list = [
    'rstr_id', 'example', 'relax', 'rstr_name', 
    'rstr_region', 'category_name', 'rstr_review_rating', 'rstr_review_count', 'distance'
]
def select_nearby(n_results, rstr_la, rstr_lo):
    query = f"""
        SELECT
            {','.join(rstr_column_array)}
            , (6371 * acos(
                cos(radians({rstr_la})) * cos(radians(rstr_la)) * cos(radians(rstr_lo) - radians({rstr_lo}))
                + sin(radians({rstr_la})) * sin(radians(rstr_la))
            )) AS distance
        FROM rstr
        {join_category}
        HAVING distance <= 10
        ORDER BY distance
        LIMIT {n_results}
    """
    return info_to_json(query, nearby_column_list)

def insert_rstrimg(item):
    item['rstr_img_url'] = []
    query = f"SELECT rstr_img_url FROM rstr_img WHERE rstr_img.rstr_id = {item['rstr_id']}"
    data = mariadbRepository.select_from_db_data(query)
    for i in range(len(data)):
        item['rstr_img_url'].append(data[i][0])
    return item
