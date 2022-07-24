from flask import Flask
from flask import request
import algorithms as a
from utilitiesFuncJSON import get_elements_for_sign_in, get_elements_for_request

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


@app.route("/getUnavailableChairs", methods=["POST"])
def get_unavailable_chairs():
    text = request.form["reservationTimeDate"]
    user, date_reservation, start_time, duration, end_time, number = get_elements_for_request(text)
    ans = a.get_unavailable_chairs_location(date_reservation, start_time, end_time)
    return ans


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
