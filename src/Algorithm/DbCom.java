package Algorithm;

import java.sql.Date;
import java.util.*;
import java.sql.*;


public class DbCom {

    Connection con;

    public DbCom() {
        String url = "jdbc:mysql://188.166.85.212:3306/Education"+
                "?verifyServerCertificate=false"+
                "&useSSL=true"+
                "&requireSSL=true";
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

    /**
    * returns studyCode related to studyName
    * might return wrong name since studyName is not unique in database
    * not prefered unless strictly nesseccary
    */
    public String getStudyCode(String studyName) {
        try {
            Statement stmt = this.con.createStatement();
            String query = "SELECT StudyCode FROM StudyProgram WHERE StudyName = " + "\"" + studyName + "\"";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getString("StudyCode");
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getStudyCode()", e);
        }
    }

    /**
    * returns a single course with courseCode courseInp
    */
    public Course getCourseSingle(String courseInp) {
        List<String> inp = new ArrayList<>();
        inp.add(courseInp);
        Map<String, Course> c = this.getCourses(inp);
        return c.get(courseInp);
    }

    /**
    * returns a map of courses with courseInp as key
    */
    public Map<String, Course> getCourses(Collection<String> courseInp) {

        StringBuilder sb = new StringBuilder();
        for(String c : courseInp) {
            sb.append("\'");
            sb.append(c);
            sb.append("\'");
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);

        Map<String, Course> courses = new HashMap<>();
        try {
            Statement courseStmt = this.con.createStatement();
            Statement dependentStmt = this.con.createStatement();
            String courseQuery = "SELECT Course.CourseCode, CourseName, Credit, Description, Faculty, ExamDate, Difficulty, TaughtInSpring, TaughtInAutumn, URL FROM Course LEFT JOIN Exam ON Exam.CourseCode = Course.CourseCode WHERE Course.CourseCode IN (" + sb + ")";
            String dependentQuery = "SELECT Dependency FROM Dependent WHERE Dependent IN (" + sb + ")";
            ResultSet courseRs = courseStmt.executeQuery(courseQuery);
            ResultSet dependentRs = dependentStmt.executeQuery(dependentQuery);

            while (courseRs.next()) {
                String courseCode = courseRs.getString("CourseCode");
                String courseName = courseRs.getString("CourseName");
                double credit = courseRs.getDouble("Credit");
                String description = courseRs.getString("Description");
                String faculty = courseRs.getString("Faculty");
                Date examDate = courseRs.getDate("ExamDate");
                int difficulty = courseRs.getInt("Difficulty");
                boolean taughtInSpring = courseRs.getBoolean("TaughtInSpring");
                boolean taughtInAutumn = courseRs.getBoolean("TaughtInAutumn");
                String url = courseRs.getString("URL");

                String season = (taughtInSpring) ? "spring" : "autumn";
                if (taughtInSpring && taughtInAutumn) {
                    season = "agile";
                }

                Course course = new Course(courseCode, season, 7.5);
                course.setCourseName(courseName);
                course.setStudypoints(credit);
                course.setDescription(description);
                course.setFaculty(faculty);
                course.setExam_Date(examDate);
                course.setDifficulty(difficulty);
                course.setURL(url);

                // add dependencies
                while (dependentRs.next()) {
                    String d = dependentRs.getString(1);
                    course.addDependency(d);
                }

                courses.put(courseCode, course);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCourse()", e);
        }

        return courses;
    }

    /**
    * returns list of courseCodes and courseNames from database as string with space in between
    */
    public Collection<String> getCoursesAsString() {
        try {
            Statement courseStmt = this.con.createStatement();
            String courseQuery = "SELECT CourseCode, CourseName FROM Course";
            ResultSet rs = courseStmt.executeQuery(courseQuery);
            Collection<String> rCol = new ArrayList<>();
            while (rs.next()) {
                String s = rs.getString("CourseCode") + ":" + rs.getString("CourseName");
                rCol.add(s);
            }
            return rCol;
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCourses()", e);
        }
    }

    /**
    * returns all the coursecodes from major with studyCode equal studyCodeInp in uppercase
    */
    public StudyPlan getCoursesFromMajor(String studyCodeInp) {
        try {
            Statement stmt1 = this.con.createStatement();
            Statement stmt2 = this.con.createStatement();
            String courseStudyQuery = "SELECT CourseCode, Semester, MandatoryString FROM CourseStudyProgram WHERE StudyCode = " + "\"" + studyCodeInp + "\"";
            ResultSet rs1 = stmt1.executeQuery(courseStudyQuery);
            ResultSet rs2 = stmt2.executeQuery(courseStudyQuery);

            // creates courseStrings which is a List containing names of all courses needed
            List<String> courseStrings = new ArrayList<>();
            while (rs1.next()) {
                courseStrings.add(rs1.getString("CourseCode"));
            }
            Map<String, Course> courseCourse = this.getCourses(courseStrings);

            // creates map with semester as key, and all courseCode in that semester as value
            Map<Integer, List<Course>> courseMap = new HashMap<>();
            while (rs2.next()) {
                String courseCode = rs2.getString("CourseCode");
                Integer semesterNumber = rs2.getInt("Semester");
                String mandatory = rs2.getString("MandatoryString");
                Course course = courseCourse.get(courseCode);
                List<Course> courseList = courseMap.get(semesterNumber);
                // removes not mandatory courses
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
                if (key % 2 == 0) {
                    season = "spring";
                } else {
                    season = "autumn";
                }
                Semester sem = new Semester(season);

                sem.addCourseList(courseMap.get(key));
                studyPlan.addSemester(sem, key);
            }

            // fills studyPlan with electives before returning
            studyPlan.fillSemesterWithElectives();

            return studyPlan;

        } catch (SQLException e) {
            throw new IllegalStateException("SQLException in DbCom.getCoursesFromMajor()", e);
        }
    }

    /**
    * returns the semester where people going 'studyCode' have 'courseCode' acording to the university studyPlan
    */
    public int getSemester(String courseCode, String studyCode) {
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
}
