import json
import datetime as d

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
    this function returns all the elements for registration, when every element is a string.
    text - string that contain JSON, must be for sign in.
    return - return the following elements in this order: user_name, password, email
"""
def get_elements_for_login(text):
    json_object = json.loads(text)
    user = json_object["userNameStudent"]
    psw = json_object["passwordStudent"]
    email = json_object["emailUniversity"]
    print(f"registration: {user},{psw},{email}")
    return user, psw, email


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
