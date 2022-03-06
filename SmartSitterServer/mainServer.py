from flask import Flask
from flask import request
import algorithms as a
import dataBaseFunctions as db
from utilitiesFunctions import get_elements_for_request

app = Flask(__name__)


@app.route("/")
def show_home_page():
    return "This is home page"


@app.route("/reservation", methods=["POST"])
def reservation_func():
    text = request.form["reservationBasicDetails"]
    flow_ans = a.choose_flow(text)
    if flow_ans == 'reservation_later':
        # insert the request to requests table.
        user, date, start_time, duration, end_time, number = get_elements_for_request(text)
        query = f"INSERT INTO requests (user_name, reservation_date, start_time, end_time, duration," \
                f" num_of_participants) VALUES ('{user}', CONVERT(datetime, '{date}',103), CONVERT(time," \
                f" '{start_time}'), CONVERT(time, '{end_time}'), CONVERT(int, '{duration}'), CONVERT(int, '{number}'))"
        db.run_insert_query(query, db.connect_db())
    return flow_ans


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
