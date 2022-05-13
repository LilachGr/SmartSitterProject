import json
import datetime as d
from dataBaseFunctions import run_insert_query, connect_db, run_select_query


"""
    this function returns all the elements for sign in, when every element is a string.
    text - string that contain JSON, must be for sign in.
    return - return the following elements in this order: user_name, password
"""
def get_elements_for_sign_in(text):
    json_object = json.loads(text)
    user = json_object["userNameStudent"]
    psw = json_object["passwordStudent"]
    print(f"sign in: {user},{psw}")
    return user, psw


"""
    this function returns all the elements for reservation request, when every element is a string.
    text - string that contain JSON, must be for reservation request.
    return - return the following elements in this order: 
                user_id, date, start_time, duration, end_time (start_time + duration), number_of_students
"""
def get_elements_for_request(text):
    json_object = json.loads(text)
    user = json_object["userNameStudent"]
    date = json_object["dateReservation"]
    start_time = d.datetime.strptime(json_object["timeReservation"], '%H:%M')
    duration = json_object["duration"]
    end_time = start_time + d.timedelta(minutes=int(duration))
    start_time = str(start_time.time())
    end_time = str(end_time.time())
    number = json_object["numberOfStudent"]
    print(f"reservation date time: {user}, {date}, {start_time}, {duration}, {end_time}, {number}")
    return user, date, start_time, duration, end_time, number


"""
    this function returns all the elements for reservation, when every element is a string.
    text - string that contain JSON, must be for reservation (include place).
    return - return the following elements in this order: 
                user_name, date, start_time, duration, end_time (start_time + duration),
                number_of_students ,building, room, chair_id
"""
def get_elements_for_reservation(text):
    user, date, start_time, duration, end_time, number = get_elements_for_request(text)
    json_object = json.loads(text)
    building = json_object["building"]
    room = json_object["room"]
    chair_id = json_object["chairId"]
    print(f"reservation full details: {user}, {date}, {start_time}, {duration}, {end_time}, {number}, {building}, {room}, {chair_id}")
    return user, date, start_time, duration, end_time, number, building, room, chair_id


"""
    insert all the reservation details to reservation table.
"""
def insert_to_reservation_table(parameters):
    user, date, start_time, duration, end_time, number, building, room, chair_id = \
        get_elements_for_reservation(parameters)
    query = f"INSERT INTO reservations (reservation_date, start_time, end_time, duration, num_of_participants, " \
            f"user_id, location_id) VALUES(CONVERT(datetime, '{date}',3), CONVERT(time, '{start_time}')," \
            f"CONVERT(time, '{end_time}'), CONVERT(int, '{duration}'),  CONVERT(int, '{number}')," \
            f"CONVERT(int, '{user}'), {get_location_id(building, room, chair_id)})"
    run_insert_query(query, connect_db())


"""
    building, room, chair_id - all string
    return location_is - int
"""
def get_location_id(building, room, chair_id):
    query = f"select id from labs where building='{building}' and room='{room}' and chairId='{chair_id}'"
    ans = run_select_query(query, connect_db())
    location_id = ans[0][0]
    print("location_id: " + str(location_id))
    return location_id


"""
    parameters: date, start_time, end_time - all string
    return all available places in the labs (building, room, chair).
"""
def get_all_available_chairs(date, start_time, end_time):
    query_time = f"select * from reservations where reservation_date=CONVERT(date, '{date}',3) " \
                 f"and ((start_time between CONVERT(time , '{start_time}', 8) and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 30, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 60, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 90, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)))"
    return run_select_query(query_time, connect_db())