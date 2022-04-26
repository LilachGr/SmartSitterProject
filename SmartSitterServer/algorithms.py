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
    return False if not available, return True if available.
"""
def check_time_availability(parameters):
    user, date, start_time, duration, end_time, number = get_elements_for_request(parameters)
    query = ''
    ans = db.run_select_query(query, db.connect_db())

