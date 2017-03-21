import MySQLdb
import json
import re
import datetime

# add " before and after string, swith None with "NULL"
def makeSQL(tup):
    newTup = []
    for arg in tup:
        newArg = None
        if arg is None:
            newArg = "NULL"
        elif type(arg) == type(""):
            newArg = []
            for char in arg:
                if char in ["\'", "\"", "\\"]:
                    newArg.append('\\' + char)
                else:
                    newArg.append(char)
            newArg = "\'" + ''.join(newArg) + "\'"
        if newArg is not None:
            newTup.append(newArg)
        else:
            newTup.append(arg)
    return tuple(newTup)

# if tupple is one element, could be the same function
def makeSQLSingle(arg):
    retVal = ''
    if arg is None:
        newArg = "NULL"
    elif type(arg) == type(""):
        newArg = []
        for char in arg:
            if char in ["\'", "\"", "\\"]:
                newArg.append('\\' + char)
            else:
                newArg.append(char)
        newArg = "\'" + ''.join(newArg) + "\'"
    if newArg is not None:
        retVal = newArg
    else:
        retVal = arg
    return retVal


#this script adds courses to database crreated by createTables.sql

CourseQuerry = "INSERT INTO Course(CourseCode, CourseName, Credit, CreditTypeCode, TaughtInSpring, TaughtInAutumn, StudyLevelCode, Difficulty, Faculty, URL, Description) VALUES({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})"
DependentQuerry = "INSERT INTO Dependent(Dependent, Dependency, Necessary) VALUES({}, {}, {})"
CreditReducitonQuerry = "INSERT INTO CreditReduction(ReducedCourse, ReducedBy, Amount) VALUES({}, {}, {})"
SubjectQuerry = "INSERT INTO Subject(SubjectCode, SubjectName) VALUES({}, {})"
StudyProgramQuerry = "INSERT INTO StudyProgram(StudyCode, StudyName) VALUES({}, {})"
LanguageQuerry = "INSERT INTO Language(LanguageName) VALUES({})"
CourseSubjectQuerry = "INSERT INTO CourseSubject(CourseCode, SubjectCode) VALUES({}, {})"
CourseStudyProgramQuerry = "INSERT INTO CourseStudyProgram(CourseCode, StudyCode, Semester) VALUES({}, {}, {})"
CourseLanguageQuerry = "INSERT INTO CourseLanguage(CourseCode, LanguageName) VALUES({}, {})"
ExamCodeQuerry = "INSERT INTO ExamCode(AidCode, AidName) VALUES({}, {})"
#AssessmentFormQuerry = "INSERT INTO AssessmentForm(AssessmentFormCode, AssessmentFormDescription) VALUES({}, {})"
ExamQuerry = "INSERT INTO Exam(CourseCode, ExamDate, RegistrationDeadLine, DeadLineBackOut, AidCode) VALUES({}, {}, {}, {}, {})"
TeacherQuerry = "INSERT INTO Teacher(TeacherId, Name, Email, Phone, OfficeAdress) VALUES({}, {}, {}, {}, {})"
TeacherCourseQuerry = "INSERT INTO CourseTeacher(CourseCode, TeacherId, Type) VALUES({}, {}, {})"

CourseArgs = [] #Done
DependentArgs = [] #Done
CreditReductionArgs = [] #Done
SubjectArgs = set() #Done
StudyProgramArgs = set() #Done
LanguageArgs = set() #Done
CourseSubjectArgs = [] #Done
CourseStudyProgramArgs = [] #need to add semester
CourseLanguageArgs = [] #Done
ExamCodeArgs = set() #Done
#AssessmentFormArgs = set() #not Done, maybe unecessary
ExamArgs = [] #Done
TeacherArgs = set() #Done
TeacherCourseArgs = [] #Done

with open('data.json', 'r') as f:   
    data = json.loads(f.read())

courseRegex = re.compile('^[A-Z]{2,6}[0-9]{2,6}$')

for course in data:
    cData = data[course]
    url = None
    description = None
    infoType = cData.get('infoType')
    if infoType is not None:
        #get information for course
        for element in infoType:
            if element['code'] == 'E-URL':
                url = element.get('text')
            elif element['code'] == 'INNHOLD':
                description = element.get('text')
            #add to table Dependent
            elif element['code'] == 'ANBFORK' or element['code'] == 'FORK':
                necessary = element['code'] == 'FORK'
                t = element.get('text')
                if t is not None:
                    t = re.split('[ /]', t)
                    for word in t:
                        if courseRegex.match(word):
                            DependentArgs.append((course, word, necessary))

    #add to table Course
    studyCode = cData['studyLevelCode']
    if studyCode == None:
        studyCode = 0
    autumn = cData['taughtInAutumn']
    if autumn == None:
        autumn = False
    spring = cData['taughtInSpring']
    if spring == None:
        spring = False
    CourseArgs.append((cData['code'], cData['name'], cData['credit'], cData['creditTypeCode'], \
    spring, autumn, studyCode, 1, None, url, description))

    #add ro table CreditRecution
    creditReduction = cData.get('creditReduction')
    if creditReduction is not None:
        for element in creditReduction:
            CreditReductionArgs.append((course, element['courseCode'], element['creditsReduction']))

    #add to table Subject and CourseSubject
    subjectArea = cData.get('subjectArea')
    if subjectArea is not None:
        for element in subjectArea:
            SubjectArgs.add((element['code'], element['name']))
            CourseSubjectArgs.append((course, element['code']))

    #add to table StudyProgram and CourseStudyProgram
    studyProgram = cData.get('usedInStudyprogrammes')
    if studyProgram is not None:
        for element in studyProgram:
            year = int(cData['studyLevelCode'])//100
            if year == 0:
                year = 1
            elif year > 5:
                year = 5
            semester = year * 2
            if autumn:
                semester -= 1
            StudyProgramArgs.add((element['code'], element['name']))
            CourseStudyProgramArgs.append((course, element['code'], semester))

    #add to table Language and CourseLanguage
    language = cData.get('educationLanguage')
    if language is not None:
        for element in language:
            LanguageArgs.add((element['name']))
            CourseLanguageArgs.append((course, element['name']))

    #add to table Exam, AssessmentForm and ExamCode
    assessment = cData.get('assessment')
    if assessment is not None:
        for element in assessment:
            if element.get('statusCode') and element.get('examinationSupport'):
                s = element.get('examinationSupport')
                for support in s:
                    if element.get('date'):
                        ExamCodeArgs.add((support['code'], support['name']))
                        ExamArgs.append((course, element['date'], element.get('registrationDeadLine'), element.get('deadlineBackOut'), support.get('code')))
    
    #add to table Teacher and TeacherCourse
    educationalRole = cData.get('educationalRole')
    if educationalRole is not None:
        for element in educationalRole:
            person = element.get('person')
            if person is not None:
                TeacherArgs.add((person['personId'], person['firstName'] + person['lastName'], person.get('email'), person.get('phone'), element.get('officeAddress')))
            if element.get('code') and element.get('personId'):
                TeacherCourseArgs.append((course, element['personId'], element['code']))

db = MySQLdb.connect(host="188.166.85.212", user="default", db="Education")
#db = MySQLdb.connect(host='localhost', user='test', db='education')

cur = db.cursor()

#Create Tables
with open('createTables.sql', 'r') as f:
   sql = " ".join(f.readlines())
cur.execute(sql)

querries = 0

def executeSQL(Querry, args):
    global querries
    for arg in args:
        querries += 1
        try:
            arg = makeSQL(arg)
            cur.execute(Querry.format(*arg))
        except Exception: #!!!
             print(Querry.format(*arg))
             continue
    print(querries)

print('course')
executeSQL(CourseQuerry, CourseArgs)

db.commit()

print('dependent')
executeSQL(DependentQuerry, DependentArgs)

db.commit()

print('credit')
executeSQL(CreditReducitonQuerry, CreditReductionArgs)

db.commit()

print('subject')
executeSQL(SubjectQuerry, SubjectArgs)

db.commit()

print('study')
executeSQL(StudyProgramQuerry, StudyProgramArgs)

db.commit()

#usese different makeSQL, maybe fix
print('language')
for arg in LanguageArgs:
    arg = makeSQLSingle(arg)
    cur.execute(LanguageQuerry.format(arg))

db.commit()

print('coursesubject')
executeSQL(CourseSubjectQuerry, CourseSubjectArgs)

db.commit()

print('coursestudy')
executeSQL(CourseStudyProgramQuerry, CourseStudyProgramArgs)

db.commit()

print('courselanguage')
executeSQL(CourseLanguageQuerry, CourseLanguageArgs)

db.commit()

print('examcode')
executeSQL(ExamCodeQuerry, ExamCodeArgs)

db.commit()

print('exam')
executeSQL(ExamQuerry, ExamArgs)

db.commit()

print('teacher')
executeSQL(TeacherQuerry, TeacherArgs)

db.commit()

print('teachercourse')
executeSQL(TeacherCourseQuerry, TeacherCourseArgs)

db.commit()

print("length = ", len(CourseArgs) + len(DependentArgs) + len(CreditReductionArgs) + len(SubjectArgs) + len(StudyProgramArgs) + len(LanguageArgs) + len(CourseSubjectArgs) + len(CourseStudyProgramArgs) + len(CourseLanguageArgs) + len(ExamCodeArgs) + len(ExamArgs) + len(TeacherArgs) + len(TeacherCourseArgs))

db.close()