from flask import Flask
from flask import request

app = Flask(__name__)


@app.route("/")
def show_home_page():
    return "This is home page"


@app.route("/reservation", methods=["POST"])
def debug():
    text = request.form["reservationBasicDetails"]

    print(text)
    return "received"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)


"""@app.route("/", methods=['GET', 'POST'])
def handle_request():
    # response from the server
    return "This is home page"""

