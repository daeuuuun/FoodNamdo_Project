import uuid, json
from decimal import Decimal

def insert_results(db, json_data):
    randomKey = str(uuid.uuid4()).encode()
    json_data = json.dumps(json_data, default=decimal_to_float)
    while True:
        if db.get(randomKey) is None:
            db.put(randomKey, json_data.encode())
            break;
        randomKey = str(uuid.uuid4()).encode()
    return randomKey.decode()

def decimal_to_float(obj):
    if isinstance(obj, Decimal):
        return float(obj)
    raise TypeError(f"Object of type {type(obj)} is not JSON serializable")