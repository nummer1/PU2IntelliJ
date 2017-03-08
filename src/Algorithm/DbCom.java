package Algorithm;

import java.util.ArrayList;
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

    public Course getCourse(String cC) {
        // get course information from database and create course
        // SELECT CourseCode, CourseName, Description, Faculty, ExamDate, Difficulty, TaughtInSpring, TaughtInAutumn
        // FROM Course JOIN Exam ON Exam.CourseCode == Course.CourseCode
        // WHERE Course.CourseCode == courseCode

        //TODO
        // get requirements
        // SELECT C.CourseCode, D.CourseCode, Necessary
        // FROM Course AS dep JOIN Dependent ON Dep.CourseCode == Dependent.CourseCode JOIN Course AS req ON req.CourseCode == Dependent.Dependency
        // WHERE dep.CourseCode == courseCode

        try {
            Statement stmt = this.con.createStatement();
            String query = "SELECT Course.CourseCode, CourseName, Description, Faculty, ExamDate, Difficulty, TaughtInSpring, TaughtInAutumn FROM Course LEFT JOIN Exam ON Exam.CourseCode = Course.CourseCode WHERE Course.CourseCode = " + "\"" + cC + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String courseCode = rs.getString("CourseCode");
                String courseName = rs.getString("CourseName");
                String description = rs.getString("Description");
                String faculty = rs.getString("Faculty");
                Date examDate = rs.getDate("ExamDate");
                int difficulty = rs.getInt("Difficulty");
                boolean taughtInSpring = rs.getBoolean("TaughtInSpring");
                boolean taughtInAutumn = rs.getBoolean("TaughtInAutumn");

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

                return course;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException, DbCom.Course()", e);
        }
    }

    //get all the courses from a given semester in a given major in uppercase
    //Ex. getCoursesFromMajor("MTDT", 2) should return an arraylist of all the courses in MTDT in the second semester
    public StudyPlan getCoursesFromMajor(String major , int semester) {
        return null;
    }

    public static void main(String[] args) {
        DbCom db = new DbCom();
        db.getCourse("TTM4100");
    }
    //possible other methods
}
