import dataBaseFunctions as db
from utilitiesFunctions import get_elements_for_request

"""
    this function decide if the reservation is now or decided by algorithm.
    parameters - string that contain JSON (text from client)
    return - "reservation_now" or "reservation_later" or "error" (must be one of this options)
"""
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
    this function check if their is place available in the requested time.
    return "time_not_available" if not available, return "time_available" if available, 
    return "user_has_this_date" if the user already booked in this date, other reasons return "error".
"""
def check_time_availability(parameters):
    user, date, start_time, duration, end_time, number = get_elements_for_request(parameters)
    query_date = f"select * from reservations where reservation_date = '{date}' and user_id = CONVERT(int, '{user}')"
    ans_date = db.run_select_query(query_date, db.connect_db())
    if len(ans_date) != 0:
        return "user_has_this_date"
    query_time = f""
    ans_time = db.run_select_query(query_time, db.connect_db())