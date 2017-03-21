CREATE TABLE Course (
    CourseCode                  VARCHAR(12),
    CourseName                  TEXT,
    Credit                      REAL,
    CreditTypeCode              CHAR(2),
    TaughtInSpring              BOOLEAN NOT NULL,
    TaughtInAutumn              BOOLEAN NOT NULL,
    StudyLevelCode              INTEGER NOT NULl,
    Difficulty                  INTEGER NOT NULL,
    Faculty                     TEXT,
    URL                         TEXT,
    Description                 TEXT,

    PRIMARY KEY (CourseCode)
);


CREATE TABLE Dependent (
    Dependent                   VARCHAR(12),
    Dependency                  VARCHAR(12),
    Necessary                   BOOLEAN,

    PRIMARY KEY (Dependent, Dependency),
    FOREIGN KEY (Dependent) REFERENCES Course(CourseCode),
    FOREIGN KEY (Dependency) REFERENCES Course(CourseCode)
);


CREATE TABLE CreditReduction (
    ReducedCourse               VARCHAR(12),
    ReducedBy                   VARCHAR(12),
    Amount                      REAL,

    PRIMARY KEY (ReducedCourse, ReducedBy),
    FOREIGN KEY (ReducedCourse) REFERENCES Course(CourseCode),
    FOREIGN KEY (ReducedBy) REFERENCES Course(CourseCode)
);


CREATE TABLE Subject (
    SubjectCode                 VARCHAR(12),
    SubjectName                 TEXT,

    PRIMARY KEY (SubjectCode)
);


CREATE TABLE StudyProgram (
    StudyCode                   VARCHAR(12),
    StudyName                   TEXT,

    PRIMARY KEY (StudyCode)
);


CREATE TABLE Language (
    LanguageName                VARCHAR(20),

    PRIMARY KEY (LanguageName)
);


CREATE TABLE CourseSubject (
    CourseCode                  VARCHAR(12),
    SubjectCode                 VARCHAR(12),

    PRIMARY KEY (CourseCode, SubjectCode),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (SubjectCode) REFERENCES Subject(SubjectCode)
);


CREATE TABLE CourseStudyProgram (
    CourseCode                  VARCHAR(12),
    StudyCode                   VARCHAR(12),
    Semester                    INTEGER,

    PRIMARY KEY (CourseCode, StudyCode),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (StudyCode) REFERENCES StudyProgram(StudyCode)
);


CREATE TABLE CourseLanguage (
    CourseCode                  VARCHAR(12),
    LanguageName                VARCHAR(20),

    PRIMARY KEY (CourseCode, LanguageName),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (LanguageName) REFERENCES Language(LanguageName)
);


CREATE TABLE ExamCode (
    AidCode                     VARCHAR(20),
    AidName                     TEXT,

    PRIMARY KEY (AidCode)
);


#CREATE TABLE AssessmentForm (
#    AssessmentFormCode          VARCHAR(2),
#    AssessmentFormDescription   VARCHAR(100),
#
#    PRIMARY KEY (AssessmentFormCode)
#);


CREATE TABLE Exam (
    CourseCode                  VARCHAR(12),
    ExamDate                    DATE,
    #AssessmentFormCode         VARCHAR(2),
    RegistrationDeadLine        DATE,
    DeadLineBackOut             DATE,
    AidCode                     VARCHAR(20),

    PRIMARY KEY (CourseCode, ExamDate),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (AidCode) REFERENCES ExamCode(AidCode)
    #FOREIGN KEY (AssessmentFormCode) REFERENCES AssessmentForm(AssessmentFormCode)
);


CREATE TABLE Teacher (
    TeacherId                   INTEGER,
    Name                        VARCHAR(50),
    Email                       TEXT,
    Phone                       VARCHAR(20),
    OfficeAdress                TEXT,

    PRIMARY KEY (TeacherID)
);


CREATE TABLE CourseTeacher (
    TeacherId                   INTEGER,
    CourseCode                  VARCHAR(12),
    Type                        TEXT,

    PRIMARY KEY (TeacherId, CourseCode),
    FOREIGN KEY (TeacherId) REFERENCES Teacher(TeacherId),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode)
);
