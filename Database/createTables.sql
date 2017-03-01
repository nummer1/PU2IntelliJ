CREATE TABLE Course (
    CourseCode                  VARCHAR(12),
    CourseName                  VARCHAR(50),
    Credit                      REAL,
    CreditTypeCode              CHAR(2),
    TaughtInSpring              BOOLEAN,
    TaughtInAutumn              BOOLEAN,
    URL                         VARCHAR(50),
    Prerequisite                VARCHAR(300),
    Content                     VARCHAR(300),

    PRIMARY KEY (CourseCode)
);


CREATE TABLE Dependent (
    Dependent                   VARCHAR(12),
    Dependency                  VARCHAR(12),

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
    SubjectName                 VARCHAR(50),

    PRIMARY KEY (SubjectCode)
);


CREATE TABLE StudyProgram (
    StudyCode                   VARCHAR(12),
    StudyName                   VARCHAR(50),

    PRIMARY KEY (StudyCode)
);


CREATE TABLE Language (
    LanguageName                VARCHAR(50),

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
    Year                        YEAR(4),

    PRIMARY KEY (CourseCode, StudyCode),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (StudyCode) REFERENCES StudyProgram(StudyCode)
);


CREATE TABLE CourseLanguage (
    CourseCode                  VARCHAR(12),
    LanguageName                VARCHAR(50),

    PRIMARY KEY (CourseCode, LanguageName),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (LanguageName) REFERENCES Language(LanguageName)
);


CREATE TABLE ExamCode (
    AidCode                     CHAR(1),
    AidName                     VARCHAR(30),

    PRIMARY KEY (AidCode)
);


CREATE TABLE AssessmentForm (
    AssessmentFormCode          VARCHAR(2),
    AssessmentFormDescription   VARCHAR(50),

    PRIMARY KEY (AssessmentFormCode)
);


CREATE TABLE Exam (
    CourseCode                  VARCHAR(12),
    ExamDate                    DATETIME,
    AssessmentFormCode          VARCHAR(2),
    RegistrationDeadLine        DATE,
    DeadLineBackOut             DATE,
    AidCode                     CHAR(1),

    PRIMARY KEY (CourseCode, ExamDate),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode),
    FOREIGN KEY (AidCode) REFERENCES ExamCode(AidCode),
    FOREIGN KEY (AssessmentFormCode) REFERENCES AssessmentForm(AssessmentFormCode)
);


CREATE TABLE Teacher (
    TeacherId                   INTEGER,
    Type                        VARCHAR(50),
    Name                        VARCHAR(50),
    Email                       VARCHAR(50),
    Phone                       INTEGER,
    OfficeAdress                VARCHAR(50),

    PRIMARY KEY (TeacherID)
);


CREATE TABLE TeacherCourse (
    TeacherId                   INTEGER,
    CourseCode                  VARCHAR(12),

    PRIMARY KEY (TeacherId, CourseCode),
    FOREIGN KEY (TeacherId) REFERENCES Teacher(TeacherId),
    FOREIGN KEY (CourseCode) REFERENCES Course(CourseCode)
);