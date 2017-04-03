import requests
import re
import MySQLdb
from bs4 import BeautifulSoup as bs


#tables gives misinformation in the HTML code
#(says it 1. year when it is 3.)
#TODO find away around problem above
#updates database with correct MandatoryString and Semester in CourseStudyProgram table


def connectToDatabase():
    return MySQLdb.connect(host = '2a03:b0c0:2:d0::1436:9001', user = 'default', db = 'Education')

# returns urls to be searched
def getUrls(studyCode):
    urlBase = 'https://www.ntnu.no/studier/{}/{}'
    altList = ['oppbygning', 'oppbygging']
    retList = []
    for element in altList:
        retList.append(urlBase.format(studyCode, element))
    return retList


# returns best guess semester from text
def getSemester(text):
    yearReg = '[1-9]\. år|[Ff]ørste|[Aa]ndre|[Tt]redje|[Ff]jerde|[Ff]emte'
    seasonReg = '[Hh]øst|[Vv]år'

    year = re.search(yearReg, text)
    season = re.search(seasonReg, text)

    y = year.group().lower()

    if y == 'første' or y == '1. år':
        grade = 1
    elif y == 'andre' or y == '2. år':
        grade = 2
    elif y == 'tredje' or y == '3. år':
        grade = 3
    elif y == 'fjerde' or y == '4. år':
        grade = 4
    elif y == 'femte' or y == '5. år':
        grade = 5
    else:
        print('Fix code in getSemester wrong semester')

    semester = grade * 2

    if season.group().lower() == 'høst':
        semester -= 1

    return semester


# return list of courses with semester for given studyCode
def getCourses(studyCode):
    retCourses = []
    reg = '>[A-Z]{2,6}[0-9]{4,6}</a>'

    for url in getUrls(studyCode):
        page = requests.get(url)
        if page.status_code == 200:
            soup = bs(page.text, 'lxml')
            tables = soup.findAll("table")
            for table in tables:
                it = re.finditer(reg, str(table))
                for match in it:
                    retCourses.append(match.group()[1:-4])
    return retCourses

#updates mandatoryString
def updateCourseStudy(courseCodeList, studyCode):
    qMan = "UPDATE CourseStudyProgram SET MandatoryString = 'O' WHERE StudyCode = '{}' AND CourseCode IN({})"
    qElect = "UPDATE CourseStudyProgram SET MandatoryString = 'V' WHERE StudyCode = '{}' AND MandatoryString IS NULL"
    courses = []
    for c in courseCodeList:
        courses.append('\'')
        courses.append(c)
        courses.append('\'')
        courses.append(', ')
    if len(courses) > 0:    
        courses.pop()
    courses = ''.join(courses)
    qMan = qMan.format(studyCode, courses)
    qElect = qElect.format(studyCode)
    cur = db.cursor()
    cur.execute(qMan)
    cur.execute(qElect)
    db.commit()

# returns list of studyprograms
def getStudies():
    studies = []
    db = connectToDatabase()
    cur = db.cursor()
    cur.execute("SELECT StudyCode FROM StudyProgram")
    for r in cur:
        studies.append(r[0])
    return studies



if __name__ == '__main__':
    db = connectToDatabase()
    studies = getStudies()
    length = len(studies)
    for study in studies:
        print(length)
        courses = getCourses(study.lower())
        if len(courses) > 0:
            updateCourseStudy(courses, study.upper())
        length -= 1
    db.commit()
    db.close()

# Where MandatoryString != 'O' SET 'V'