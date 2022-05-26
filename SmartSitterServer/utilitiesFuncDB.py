from dataBaseFunctions import run_insert_query, connect_db, run_select_query
from utilitiesFuncJSON import get_elements_for_reservation

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
    return all unavailable places in the labs (building, room, chair).
"""
def get_all_unavailable_chairs(date, start_time, end_time):
    query_time = f"select * from reservations where reservation_date=CONVERT(date, '{date}',3) " \
                 f"and ((start_time between CONVERT(time , '{start_time}', 8) and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 30, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 60, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)) " \
                 f"or (CONVERT(time,DATEADD(MINUTE, 90, start_time),8) between CONVERT(time , '{start_time}', 8) " \
                 f"and CONVERT(time , '{end_time}', 8)))"
    return run_select_query(query_time, connect_db())


"""
    insert all the login details to login table.
"""
def insert_to_login_table(user, psw, email):
    query = f"INSERT INTO users (user_name, email, pwd) VALUES('{user}', '{email}', '{psw}')"
    run_insert_query(query, connect_db())


"""
    insert all room to table labs. notice the chair_id is unique to the room.
    this action should be done once for lab.
    this is a private function, and shouldn't be used outside this file.
"""
def __insert_to_lab(building, room, start_id, end_id, silence_room):
    for chair_id in range(start_id, end_id):
        query = f"INSERT INTO labs (building, room, chairId, silence_room) VALUES('{building}', '{room}', '{chair_id}'" \
                f", '{silence_room}')"
        run_insert_query(query, connect_db())


#__insert_to_lab(604, 202, 1, 13*3+1, 'true')