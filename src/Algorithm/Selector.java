package Algorithm;

import java.util.*;

public class Selector {

    private DbCom db = new DbCom();

    /**
     * This function acts as the main algorithm, which the front end runs to make a new studyplan for the students.
     * @param finishedCourses an Arraylist of the courses the student has finished
     * @param toName a String of the major the student is switching to (e.g MTDT)
     * @param season a String which denotes what season the studyplan should start in
     * @return Studyplan - The finished studyplan object
     */
    public StudyPlan switchMajor( ArrayList<Course> finishedCourses, String toName, String season) {
        StudyPlan majorCourses = db.getCoursesFromMajor(toName);
        ArrayList<Course> neededCourses = majorCourses.getCourses();
        neededCourses.removeAll(finishedCourses);

        //Give courses score based on what year they are in
        for (Course course : neededCourses) {
            int semester;
            if (course.getCourseId().substring(0, 4).equals("valg")) {
                String code = course.getCourseId();
                semester = Integer.parseInt(code.substring(code.length() - 1));
            } else {
                semester = db.getSemester(course.getCourseId(), toName);
            }
            if (semester < 2) {
                course.setScore(1000);
            } else if (semester < 4) {
                course.setScore(800);
            } else if (semester < 6) {
                course.setScore(600);
            } else if (semester < 8) {
                course.setScore(400);
            } else if (semester < 10) {
                course.setScore(200);
            } else {
                course.setScore(100);
            }
        }

        //Give courses score based on dependencies
        for (Course course : neededCourses) {
            ArrayList<String> dependencies = course.getDependencies();
            for (Course course1 : neededCourses) {
                Double d = 1.0;
                if(dependencies.contains(course1.getCourseId())) {
                    d += 0.2;
                }
                course1.setScore(course1.getScore() * d);
            }
        }

        //Sort the courses based on the score
        Collections.sort(neededCourses);

        //Set courses in semester based on the sorted set of courses
        StudyPlan studyplan = new StudyPlan("Custom studyplan");
        boolean autumn = season.equals("autumn");
        int semNumber = 1;
        while (!neededCourses.isEmpty()) {
            Semester semester = new Semester((autumn) ? "autumn" : "spring");
            int i = 1;
            while (!semester.isFilled() && !neededCourses.isEmpty()) {
                if (i > neededCourses.size()) {
                    break;
                }
                Course c = neededCourses.get(neededCourses.size() - i);
                Boolean isAllowdThisSeason = c.isAgile() || c.isSpring() && semester.isSpring() || c.isAutumn() && semester.isAutumn();
                // if season do not match, dont put in studyplan, check course equals agile or equal semester season
                if (semester.getStudypoints() <= 30.0 - c.getStudypoints() && isAllowdThisSeason) {
                    semester.addCourse(c);
                    neededCourses.remove(c);
                    System.out.println("REMOVED A COURSE");
                    System.out.println(neededCourses);
                } else {
                    if (i <= neededCourses.size()) {
                        i++;
                    } else {
                        System.out.println("BREAK");
                        break;
                    }
                }
                if (semester.isFilled()) {
                    break;
                }
            }
            /*for(int j=0; j<4; j++) {
                if (!stack.isEmpty()) {
                    semester.addCourse(stack.pop());
                }
            }*/
            autumn = !autumn;
            studyplan.addSemester(semester, semNumber);
            semNumber += 1;
        }

        return studyplan;
    }
}
