import MySQLdb
import json


CourseQuerry = "INSERT INTO Course(CourseCode, CourseName, Credit, CreditTypeCode, " \
    "TaughtInSpring, TaughtInAutumn, URL, Prerequisite, Content) " \
    "VALUES({}, {}, {}, {}, {}, {}, {}, {}, {})"
DependentQuerry = "INSERT INTO Dependent(Dependent, Dependency) VALUES({}, {})"
CreditReducitonQuerry = "INSERT INTO CourseReduction(ReducedCourse, ReducedBy, Amount) VALUES({}, {}, {})"
SubjectQuerry = "INSERT INTO Subject(SubjectCode, SubjectName) VALUES({}, {})"
StudyProgramQuerry = "INSERT INTO Studyrogram(StudyCode, StudyName VALUES({}, {})"
LanguageQuerry = "INSERT INTO Language(LanguageName) VALUES({})"
CourseSubjectQuerry = "INSERT INTO CourseSubject(CourseCode, SubjectCode) VALUES({}, {})"
CourseStudyProgramQuerry = "INSERT INTO CourseStudyProgram(CourseCode, StudyCode, Semester) VALUES({}, {}, {})"
CourseLanguageQuerry = "INSERT INTO CourseLanguage(CourseCode, Language) VALUES({}, {})"
ExamCode = "INSERT INTO ExamCode(AidCode, AidName) VALUES({}, {})"
AssessmentForm = "INSERT INTO AssessmentForm(AssessmentFormCode, AssessmentFormDescription) VALUES({}, {})"
ExamQuerry = "INSERT INTO Exam(CourseCode, ExamDate, AssessmentFormCode, RegistrationDeadLine, " \
    "DeadLineBackOut, AidCode) VALUES({}, {}, {}, {}, {}, {})"
TeacherQuerry = "INSERT INTO Teacher(TeacherId, Type, Name, Email, Phone, OfficeAdress) VALUES({}, {}, {}, {}, {}, {})"
TeacherCourseQuerry = "INSERT INTO TeacherCourse(CourseCode, TeacherId) VALUES({}, {})"

CourseArgs = []
DependetArgs = []
CreditReductionArgs = []
SubjectArgs = []
StudyProgramArgs = []
LanguageArgs = [("Norwegian"), ("English")]
CourseSubjectArgs = []
CourseStudyProgramArgs = []
CoruseLanguageArgs = []
ExamCodeArgs = []
AssessmentFormArgs = []
ExamArgs = []
TeacherArgs = []
TeacherCodeArgs = []

with open('data.json') as f:
    data = json.loads(f.read())

for course in data:
    pass


db = MySQLdb.connect(host="localhost", user="test", db="education")

#cur = db.cursor()

#cur.execute("SELECT * FROM YOUR_TABLE_NAME")

db.close()