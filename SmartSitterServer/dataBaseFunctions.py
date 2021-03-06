import pyodbc
import configServer as c # File with the correct parameters for connection to DB, to get the file please contact Lilach.

"""
    function that create connection.
    return:
        pyodbc connection. 
"""
def connect_db():
    conn = pyodbc.connect(
        'DRIVER={SQL Server};SERVER=' + c.server + ';DATABASE=' + c.database + ';UID=' + c.username + ';PWD=' + c.password)
    return conn


"""
    function that execute select query.
    parameter:
        query- string that represent correct select query. For example: "SELECT * FROM users"
    return:
        list of all rows that received from the query. 
"""
def run_select_query(query):
    with connect_db() as conn:
        with conn.cursor() as cursor:
            print(f"query: {query}")
            cursor.execute(query)
            ans = cursor.fetchall()
            print(f"ans: {ans}")
    return ans


"""
    function that execute insert query.
    parameter:
        query- string that represent correct insert query.
"""
def run_insert_query(query):
    with connect_db() as conn:
        with conn.cursor() as cursor:
            print(f"query: {query}")
            cursor.execute(query)
