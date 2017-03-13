package Algorithm;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            String dependentQuery = "SELECT Dependency FROM Dependent WHERE Dependency = " + "\"" + courseInp + "\"";
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
                course.setCourse_name(courseName);
                course.setDescription(description);
                course.setFaculty(faculty);
                course.setExam_Date(examDate);
                course.setDifficulty(difficulty);

                // add dependencies
                while (dependentRs.next()) {
                    course.addDependency(dependentRs.getString("Dependency "));
                }

                return course;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException("SQLException in DbCom.getCourse()", e);
        }
    }

    //get all the courses in a given major in uppercase
    public StudyPlan getCoursesFromMajor(String studyCodeInp) {
        //SELECT Course FROM CourseStudyProgram WHERE StudyCode = studyCodeInp
        try {
            Statement studyCoursestmt = this.con.createStatement();
            String courseStudyQuery = "SELECT CourseCode FROM CourseStudyProgram WHERE StudyCode = " + "\"" + studyCodeInp + "\"";
            ResultSet studyCourseRs = studyCoursestmt.executeQuery(courseStudyQuery);

            Map<Integer, List<Course>> courseMap = new HashMap<>();
            while (studyCourseRs.next()) {
                String courseCode = studyCourseRs.getString("CourseCode");
                Integer semesterNumber = studyCourseRs.getInt("Semester");
                Course course = this.getCourse(courseCode);
                List<Course> courseList = courseMap.get(semesterNumber);
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

            //create studyplan with the semesters created from courses in courseMap
            StudyPlan studyPlan = new StudyPlan(studyCodeInp);

            String season;
            for (Integer key : courseMap.keySet()) {
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
            throw new IllegalArgumentException("SQLException in DbCom.getCoursesFromMajor()");
        }
    }

    public static void main(String[] args) {
        DbCom db = new DbCom();
        Course c = db.getCourse("TTM4100");
        StudyPlan s = db.getCoursesFromMajor("MTDT");
        System.out.println(c.getCourse_id());
        System.out.println(c.getDependencies());
        System.out.println(s.getMajor());
    }
    //possible other methods
}