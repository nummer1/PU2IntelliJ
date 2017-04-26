package Algorithm;

import java.util.*;

//import com.google.gson.*;

/**
 * Created by Erlend on 15.02.2017.
 */

public class Selector {

    private DbCom db = new DbCom();

    //This would be the initial call to our main algorithm.
    //It may have many different helping functions which can be implemented when needed.
    public StudyPlan switchMajor( ArrayList<Course> finishedCourses, String toName, String season) {
        StudyPlan majorCourses = db.getCoursesFromMajor(toName);
        ArrayList<Course> neededCourses = majorCourses.getCourses();
        System.out.println(majorCourses);
        System.out.println(finishedCourses);
        neededCourses.removeAll(finishedCourses);
        //remove sall courses with id = "valg"
        //Course rC = new Course("valg", "agile", 7.5);
        //while (neededCourses.remove(rC)) { }
        System.out.println(neededCourses);

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

        //return the studyplan
        System.out.println(studyplan);
        return studyplan;
    }

/*    public StudyPlan dumbSwitchMajor(Collection<Course> from, Collection<Course> to, int semesters) {
        Collection<Course> neededCourses = new ArrayList<>(to);
        neededCourses.removeAll(from);
        Stack<Course> stack = new Stack<>();
        stack.addAll(neededCourses);
        StudyPlan studyplan = new StudyPlan("Custom studyplan");

        boolean autumn = semesters%2 == 0;
        int semNumber = 1;
        while (!stack.isEmpty()) {
            Semester semester = new Semester((autumn) ? "autumn" : "spring");
            for(int j=0; j<4; j++) {
                if (!stack.isEmpty()) {
                    semester.addCourse(stack.pop());
                }
            }
            autumn = !autumn;
            studyplan.addSemester(semester, semNumber);
            semNumber += 1;
        }
        return studyplan;
    }*/
}
