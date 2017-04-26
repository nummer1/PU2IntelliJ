import re
import MySQLdb


def connectToDatabase():
    return MySQLdb.connect(host = '2a03:b0c0:2:d0::1436:9001', user = 'default', db = 'Education')
    #return MySQLdb.connect(host = 'localhost', user = 'test', db = 'education')


if __name__ == '__main__':
    #base = "UPDATE Course SET Credit = 7.5 WHERE CourseCode = '{}'"
    db = connectToDatabase()
    cur = db.cursor()
    cur.execute("SELECT CourseCode, URL From Course")
    courseCodes = []
    for r in cur:
        if r[1] is None:
            courseCodes.append(r[0])
    db.close()
    db = connectToDatabase()
    cur = db.cursor()
    for course in courseCodes:
        base = "UPDATE Course SET URL = 'https://www.ntnu.edu/studies/courses/{}' WHERE CourseCode = '{}'"
        q = base.format(course, course)
        print(q)
        cur.execute(q)
        db.commit()
    db.close()