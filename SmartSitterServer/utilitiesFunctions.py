import json
import datetime as d

"""
    this function returns all the elements for reservation request, when every element is a string.
    text - string that contain JSON, must be for reservation request.
    return - return the following elements in this order: 
                user_name, date, start_time, duration, end_time (start_time + duration), number_of_students
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
    print(user, date, start_time, duration, end_time, number)
    return user, date, start_time, duration, end_time, number
