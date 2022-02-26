from flask import Flask
from flask import request
from algorithms import choose_flow

app = Flask(__name__)


@app.route("/")
def show_home_page():
    return "This is home page"


@app.route("/reservation", methods=["POST"])
def reservation_func():
    text = request.form["reservationBasicDetails"]
    return choose_flow(text)


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
