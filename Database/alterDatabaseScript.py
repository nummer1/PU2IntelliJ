import MySQLdb

fileName = 'alterMTDTcorrect.sql'

db = MySQLdb.connect(host="188.166.85.212", user="default", db="Education")
cur = db.cursor()

with open(fileName, 'r') as f:
    for sql in f.readlines():
        cur.execute(sql)

db.commit()
db.close()