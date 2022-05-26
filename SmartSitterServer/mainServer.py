from flask import Flask
from flask import request
import algorithms as a
from utilitiesFuncJSON import get_elements_for_sign_in, get_elements_for_login

app = Flask(__name__)


@app.route("/")
def show_home_page():
    return "This is home page"


@app.route("/timeAvailability", methods=["POST"])
def time_availability_func():
    text = request.form["reservationBasicDetails"]
    ans = a.check_time_availability(text)
    return ans


@app.route("/reservation", methods=["POST"])
def reservation_func():
    text = request.form["reservationAllDetails"]
    return a.check_lab_choice(text)


@app.route("/signIn", methods=["POST"])
def sign_in_func():
    text = request.form["signInDetails"]
    user, psw = get_elements_for_sign_in(text)
    ans = a.check_sign_in(user, psw)
    return ans


@app.route("/login", methods=["POST"])
def login_func():
    text = request.form["loginDetails"]
    ans = a.handle_login(text)
    return ans


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)


    """flow_ans = a.choose_flow(text)
    if flow_ans == 'reservation_later':
        # insert the request to requests table.
        user, date, start_time, duration, end_time, number = get_elements_for_request(text)
        query = f"INSERT INTO requests (user_name, reservation_date, start_time, end_time, duration," \
                f" num_of_participants) VALUES ('{user}', CONVERT(datetime, '{date}',103), CONVERT(time," \
                f" '{start_time}'), CONVERT(time, '{end_time}'), CONVERT(int, '{duration}'), CONVERT(int, '{number}'))"
        db.run_insert_query(query, db.connect_db())"""


    """    val serverLoginForm = "login"
    val serverSignInForm = "signIn"
    val serverAllReservation = "allReservation"""

