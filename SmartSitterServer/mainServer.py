from flask import Flask
from flask import request

app = Flask(__name__)


@app.route("/")
def show_home_page():
    return "This is home page"


@app.route("/debug", methods=["POST"])
def debug():
    text = request.form["sample"]
    text2 = request.form["hello"]

    print(text)
    print(text2)
    return "received"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)


"""@app.route("/", methods=['GET', 'POST'])
def handle_request():
    # response from the server
    return "This is home page"""

