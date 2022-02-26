"""
    this function decide if the reservation is now or decided by algorithm.
    parameters - string that contain jJSON (text from client)
    return - "reservation_now" or "reservation_later" or "error" (must be one of this options)
"""


def choose_flow(parameters):
    print(parameters)
    return "reservation_now"
    # return "reservation_later"
    # return "error"
