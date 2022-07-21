import json
from datetime import datetime
import dataBaseFunctions as db
from utilitiesFuncDB import get_all_unavailable_chairs, insert_to_login_table, get_location_id, \
    insert_to_reservation_table, get_location_details
from utilitiesFuncJSON import get_elements_for_request, get_elements_for_login, get_elements_for_reservation


"""
    this function check if their is place available in the requested time.
    return "time_not_available" if not available, return "time_available" if available, 
    return "user_has_this_date" if the user already booked in this date,
    return "smaller_date_time" if the chosen date and time are smaller then the current date.
"""
def check_time_availability(parameters):
    user, date_reservation, start_time, duration, end_time, number = get_elements_for_request(parameters)
    # check 1
    query_date = f"select * from reservations where reservation_date = CONVERT(date, '{date_reservation}',3) " \
                 f"and user_id = CONVERT(int, '{user}')"
    ans_date = db.run_select_query(query_date, db.connect_db())
    if len(ans_date) != 0:
        return "user_has_this_date"
    # check 2
    today = datetime.now()
    d = date_reservation.split('/')
    start_date_time = datetime.strptime(f"{d[0] + '/' + d[1] + '/' + '20' + d[2]} {start_time}", "%d/%m/%Y %H:%M:%S")
    print(f"check if {start_date_time} < {today} : {start_date_time < today}")
    if start_date_time < today:
        return "smaller_date_time"
    # check 3
    query_num_of_labs = f"select count(*) from labs"
    ans_num_of_labs = db.run_select_query(query_num_of_labs, db.connect_db())
    ans_available_chairs = get_all_unavailable_chairs(date_reservation, start_time, end_time)
    if len(ans_available_chairs) >= ans_num_of_labs[0][0]:
        return "time_not_available"
    return "time_available"


"""
    this function check if the user is valid user with the correct password.
    return "error" or user_id
"""
def check_sign_in(user, psw):
    query = f"select * from users where user_name ='{user}' and pwd ='{psw}'"
    ans = db.run_select_query(query, db.connect_db())
    if len(ans) != 1:
        return "error"
    user_id = ans[0][3]
    return str(user_id)


"""
    parameters - string that contain JSON (text from client)
    this function check if the login details are valid and insert them to the correct table.
    return "error" or "true"
"""
def handle_login(parameters):
    user, psw, email = get_elements_for_login(parameters)
    # checks:
    query_user_exist = f"select * from users where user_name ='{user}'"
    ans_user_exist = db.run_select_query(query_user_exist, db.connect_db())
    if len(ans_user_exist) != 0:
        return "error"
    query_email_exist = f"select * from users where email ='{email}'"
    ans_email_exist = db.run_select_query(query_email_exist, db.connect_db())
    if len(ans_email_exist) != 0:
        return "error"
    if user == '' or psw == '' or email == '':
        return "error"
    # insert to table:
    insert_to_login_table(user, psw, email)
    return "true"


""""""
def check_lab_choice(parameters):
    user, date, start_time, duration, end_time, number, building, room, chair_id = \
        get_elements_for_reservation(parameters)
    # checks:
    all_available_chairs = get_all_unavailable_chairs(date, start_time, end_time)
    location_id = get_location_id(building, room, chair_id)
    for row in all_available_chairs:
        if location_id == row[7]:
            return "error"
    # insert to table:
    insert_to_reservation_table(parameters)
    return "true"


def get_unavailable_chairs_location(date_reservation, start_time, end_time):
    ans = get_all_unavailable_chairs(date_reservation, start_time, end_time)
    if len(ans) == 0:
        return ""
    list_of_chairs_id = []
    for row in ans:
        chair = row[7]
        list_of_chairs_id.append(chair)
    location_details = get_location_details(list_of_chairs_id)
    json_data = []
    for row in location_details:
        data = {'building': str(row[0]), 'room': str(row[1]), 'chairId': str(row[2]), 'id': str(row[3])}
        json_data.append(json.dumps(data))
    j = json.loads(json.dumps(json_data))
    return str(j)

# ans2 = get_unavailable_chairs_location("12/12/22", "21:00", "22:00")
# print(f"s:{ans2}")

"""
    this function decide if the reservation is now or decided by algorithm.
    parameters - string that contain JSON (text from client)
    return - "reservation_now" or "reservation_later" or "error" (must be one of this options)

def choose_flow(parameters):
    print(parameters)
    # example for connection to DB:
    conn = db.connect_db()
    ans = db.run_select_query("SELECT * FROM users", conn)
    print(ans)
    return "reservation_now"
    # return "reservation_later"
    # return "error"
"""
