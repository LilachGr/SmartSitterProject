import pyodbc
import config as c

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
    function that execute query.
    parameter:
        query- string that represent correct query. For example: "SELECT * FROM users"
        conn- connection that received from the function connect_db()
    return:
        list of all rows that received from the query. 
"""
def run_query(query, conn):
    with conn.cursor() as cursor:
        print(f"query: {query}")
        cursor.execute(query)
        ans = cursor.fetchall()
        print(f"ans: {ans}")
    return ans
