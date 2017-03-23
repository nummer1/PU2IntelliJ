package Algorithm;

import GUI.Study;

import java.sql.Date;
import java.util.*;
import java.sql.*;


/**
 * Created by kasparov on 01.03.17.
 */

public class DbCom {

    Connection con;

    public DbCom() {
        String url = "jdbc:mysql://188.166.85.212:3306/Education";
        String username = "default";
        String password = "";
        this.createConnection(url, username, password);
    }

    private void createConnection(String url, String username, String password) {
        System.out.println("Connecting to database...");
        try {
            this.con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public Course getCourse(String courseInp) {
        // get course information from database and create course
        // SELECT CourseCode, CourseName, Description, Faculty, ExamDate, Difficulty, TaughtInSpring, TaughtInAutumn
        // FROM Course JOIN Exam ON Exam.CourseCode == Course.CourseCode
        // WHERE Course.CourseCode == courseCode

        // get requirements
        // SELECT C.CourseCode, D.CourseCode, Necessary
        // FROM Course AS dep JOIN Dependent ON Dep.CourseCode == Dependent.CourseCode JOIN Course AS req ON req.CourseCode == Dependent.Dependency
        // WHERE dep.CourseCode == courseCode

        try {
            Statement courseStmt = this.con.createStatement();
            Statement dependentStmt = this.con.createStatement();
            String courseQuery = "SELECT Course.CourseCode, CourseName, Description, Faculty, ExamDate, Difficulty, TaughtInSpring, TaughtInAutumn FROM Course LEFT JOIN Exam ON Exam.CourseCode = Course.CourseCode WHERE Course.CourseCode = " + "\"" + courseInp + "\"";
            String dependentQuery = "SELECT Dependency FROM Dependent WHERE Dependent = " + "\"" + courseInp + "\"";
            ResultSet courseRs = courseStmt.executeQuery(courseQuery);
            ResultSet dependentRs = dependentStmt.executeQuery(dependentQuery);

            if (courseRs.next()) {
                String courseCode = courseRs.getString("CourseCode");
                String courseName = courseRs.getString("CourseName");
                String description = courseRs.getString("Description");
                String faculty = courseRs.getString("Faculty");
                Date examDate = courseRs.getDate("ExamDate");
                int difficulty = courseRs.getInt("Difficulty");
                boolean taughtInSpring = courseRs.getBoolean("TaughtInSpring");
                boolean taughtInAutumn = courseRs.getBoolean("TaughtInAutumn");

                String season = (taughtInSpring) ? "spring" : "autumn";
                if (taughtInSpring && taughtInAutumn) {
                    season = "agile";
                }

                Course course = new Course(courseCode, season);
                course.setCourseName(courseName);
                course.setDescription(description);
                course.setFaculty(faculty);
                course.setExam_Date(examDate);
                course.setDifficulty(difficulty);

                // add dependencies
                while (dependentRs.next()) {
                    String d = dependentRs.getString(1);
                    course.addDependency(d);
                }

                return course;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCourse()", e);
        }
    }

    public Collection<String> getCourses() {
        // return all course codes and courseNames
        // seperate scoursecode from respective coursename with space
        // SELECT CourseCode, CourseName FROM Course
        try {
            Statement courseStmt = this.con.createStatement();
            String courseQuery = "SELECT CourseCode, CourseName FROM Course";
            ResultSet rs = courseStmt.executeQuery(courseQuery);
            Collection<String> rCol = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("CourseCode") + " " + rs.getString("CourseName");
                rCol.add(s);
            }
            return rCol;
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCourses()", e);
        }
    }

    public StudyPlan getCoursesFromMajor(String studyCodeInp, int to) {
        StudyPlan tempSp = this.getCoursesFromMajor(studyCodeInp);
        StudyPlan returnSp = new StudyPlan(studyCodeInp);
        for (int i = 1; i <= to; i++) {
            returnSp.addSemester(tempSp.getSemester(i), i);
        }
        return returnSp;
    }

    //get all the courses in a given major in uppercase
    public StudyPlan getCoursesFromMajor(String studyCodeInp) {
        //SELECT Course FROM CourseStudyProgram WHERE StudyCode = studyCodeInp
        try {
            Statement studyCoursestmt = this.con.createStatement();
            String courseStudyQuery = "SELECT CourseCode, Semester, MandatoryString FROM CourseStudyProgram WHERE StudyCode = " + "\"" + studyCodeInp + "\"";
            ResultSet studyCourseRs = studyCoursestmt.executeQuery(courseStudyQuery);

            Map<Integer, List<Course>> courseMap = new HashMap<>();
            boolean addElect = false;
            while (studyCourseRs.next()) {
                String courseCode = studyCourseRs.getString("CourseCode");
                Integer semesterNumber = studyCourseRs.getInt("Semester");
                String mandatory = studyCourseRs.getString("MandatoryString");
                Course course = this.getCourse(courseCode);
                List<Course> courseList = courseMap.get(semesterNumber);
                if (mandatory == null || (!mandatory.equals("VA") && !mandatory.equals("VB") && !mandatory.equals("V"))) {
                    if (courseList == null) {
                        //add a list with key semesterNumber to courseMap that contains course
                        List<Course> list = new ArrayList<>();
                        list.add(course);
                        courseMap.put(semesterNumber, list);
                    } else {
                        //add a course to existing list in courseMap with correct semesterNumber
                        courseList.add(course);
                    }
                }
            }

            //create studyplan with the semesters created from courses in courseMap
            StudyPlan studyPlan = new StudyPlan(studyCodeInp);

            String season;
            for (int i = 1; i <= 10; i++) {
                courseMap.computeIfAbsent(i, k -> new ArrayList<Course>());
            }
            for (Integer key : courseMap.keySet()) {
                while (courseMap.get(key).size() < 4) {
                    Course elect = new Course("valg", "agile");
                    elect.setCourseName("Valgfag");
                    courseMap.get(key).add(elect);
                }

                if (key % 2 == 0) {
                    season = "spring";
                } else {
                    season = "autumn";
                }
                Semester sem = new Semester(season);
                sem.addCourseList(courseMap.get(key));
                studyPlan.addSemester(sem, key);
            }

            return studyPlan;

        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCoursesFromMajor()", e);
        }
    }

    public int getSemester(String courseCode, String studyCode) {
        // reuturn the respective semester from coursecode and studycode
        // SELECT Semester FROM CourseStudyProgram WHERE CourseCode = courseCode AND StudyCode = studyCode
        try {
            Statement stmt = this.con.createStatement();
            String query = "SELECT Semester FROM CourseStudyProgram WHERE CourseCode = " + "\"" + courseCode + "\"" + "AND StudyCode = " + "\"" + studyCode + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("Semester");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getSemester()", e);
        }
    }
    //possible other methods
}