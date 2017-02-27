CREATE TABLE Course (
    Code                        VARCHAR(12),
    Name                        VARCHAR(30),
    Credit                      REAL,
    CreditTypeCode              CHAR(2),
    StudyLevelCode              INTEGER,
    StudyLevelStudyLevelName    CHAR(2),
    TaughtInSpring              BOOLEAN,
    TaughtInAutumn              BOOLEAN,
    URL                         VARCHAR(50),
    Forkunnskap                 VARCHAR(300),
    Innhold                     VARCHAR(300),

    PRIMARY KEY (Code)
    #CONSTRAINT chk_CourseCode CHECK (Code REGEXP '^[A-Z](2,6)[0-9](4,6)$')
);


CREATE TABLE Dependent (
    Dependent                   VARCHAR(12),
    Dependency                  VARCHAR(12),

    PRIMARY KEY (Dependent, Dependency),
    FOREIGN KEY (Dependent) REFERENCES Course(Code),
    FOREIGN KEY (Dependency) REFERENCES Course(Code)
);


CREATE TABLE CreditReduction (
    ReducedCourse               VARCHAR(12),
    ReducedBy                   VARCHAR(12),
    Amount                      REAL,

    PRIMARY KEY (ReducedCourse, ReducedBy),
    FOREIGN KEY (ReducedCourse) REFERENCES Course(Code),
    FOREIGN KEY (ReducedBy) REFERENCES Course(Code)
);


CREATE TABLE Subject (
    Code                        VARCHAR(12),
    Name                        VARCHAR(30),

    PRIMARY KEY (Code)
);


CREATE TABLE StudyProgram (
    Code                        VARCHAR(12),
    Name                        VARCHAR(30),

    PRIMARY KEY (Code)
);


CREATE TABLE Language (
    Name                        VARCHAR(20),

    PRIMARY KEY (Name)
);


CREATE TABLE CourseSubject (
    CourseCode                  VARCHAR(12),
    SubjectCode                 VARCHAR(12),

    PRIMARY KEY (CourseCode, SubjectCode),
    FOREIGN KEY (CourseCode) REFERENCES Course(Code),
    FOREIGN KEY (SubjectCode) REFERENCES Subject(Code)
);


CREATE TABLE CourseStudyProgram (
    CourseCode                  VARCHAR(12),
    StudyProgramCode            VARCHAR(12),

    PRIMARY KEY (CourseCode, StudyProgramCode),
    FOREIGN KEY (CourseCode) REFERENCES Course(Code),
    FOREIGN KEY (StudyProgramCode) REFERENCES StudyProgram(Code)
);


CREATE TABLE CourseLanguage (
    CourseCode                  VARCHAR(12),
    LanguageName                VARCHAR(20),

    PRIMARY KEY (CourseCode, LanguageName),
    FOREIGN KEY (CourseCode) REFERENCES Course(Code),
    FOREIGN KEY (LanguageName) REFERENCES Language(Name)
);


CREATE TABLE ExamCode (
    Code                        CHAR(1),
    Name                        VARCHAR(30),

    PRIMARY KEY (Code)
);


CREATE TABLE Exam (
    Code                        VARCHAR(2),
    ExamDate                    DATE,
    StatusCode                  VARCHAR(10),
    AssessmentFormCode          VARCHAR(2),
    AssessmentFormDescription   VARCHAR(30),
    RegistrationDeadLine        DATE,
    DeadLineBackOut             DATE,
    ExaminationCode             CHAR(1),

    PRIMARY KEY (Code, ExamDate),
    FOREIGN KEY (Code) REFERENCES Course(Code),
    FOREIGN KEY (ExaminationCode) REFERENCES ExamCode (Code)
);


CREATE TABLE Teacher (
    ID                          INTEGER,
    Type                        VARCHAR(20),
    Name                        VARCHAR(40),
    Email                       VARCHAR(40),
    Phone                       INTEGER,
    OfficeAdress                VARCHAR(50),

    PRIMARY KEY (ID)
);