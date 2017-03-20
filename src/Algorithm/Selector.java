package Algorithm;

import java.util.*;

//import com.google.gson.*;

/**
 * Created by Erlend on 15.02.2017.
 */

public class Selector {

    //This will be called by the model. It chooses the correct function based on the json from api.ai
    public void action(String json_filename) {
        //TODO
    }

    //This would be the initial call to our main algorithm.
    //It may have many differt helping functions which can be implemented when needed.
    public StudyPlan switchMajor(Collection<Course> from, Collection<Course> to, String toName, int semesters) {
        ArrayList<Course> neededCourses = new ArrayList<>(to);
        DbCom db = new DbCom();
        neededCourses.removeAll(from);

        for (Course course : neededCourses) {
            int semester = db.getSemester(course.getCourseId(), toName);
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
                if(dependencies.contains(course1.getCourseId())) {
                    Double d = course1.getScore()*1.2;
                    course1.setScore(d.intValue());
                }
            }
        }

        //Sort the courses based on the score
        Collections.sort(neededCourses);

        //Set courses in semester based on the sorted set of courses
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

        //return the studyplan
        return studyplan;
    }

    public StudyPlan dumbSwitchMajor(Collection<Course> from, Collection<Course> to, int semesters) {
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
    }


    //Takes in information gotten from the database and puts it into a Algorithm.Course object.
    private Course convertToCourse(Dictionary course) {
        //TODO
        return new Course("TDT4100", "spring");
    }

    //This function is only for demonstration purposes
    //Do not call this function unless you only want a static list over the first
    //8 courses in mtdt :)
    public static ArrayList<ArrayList<Course>> get_first_year() {
        ArrayList<ArrayList<Course>> plan = new ArrayList<>();
        ArrayList<Course> first = new ArrayList<>();
        ArrayList<Course> second = new ArrayList<>();

        Course tdt4110 = new Course("TDT4110", "autumn");
        tdt4110.setCourseName("Informasjonsteknologi, grunnkurs");
        tdt4110.setExamDate("12/12/2017");
        first.add(tdt4110);

        Course tma4100 = new Course("TMA4100", "autumn");
        tma4100.setCourseName("Matematikk 1");
        tma4100.setExamDate("08/12/2017");
        first.add(tma4100);

        Course tma4140 = new Course("TMA4140", "autumn");
        tma4140.setCourseName("Diskret matematikk");
        tma4140.setExamDate("02/12/2017");
        first.add(tma4140);

        Course exph0004 = new Course("EXPH0004", "autumn");
        exph0004.setCourseName("Examen philosophicum for naturvitenskap og teknologi");
        exph0004.setExamDate("27/11/2017");
        first.add(exph0004);

        Course tdt4100 = new Course("TDT4100", "spring");
        tdt4100.setCourseName("Objektorientert programmering");
        tdt4100.setExamDate("15/05/2017");
        second.add(tdt4100);

        Course tdt4112 = new Course("TDT4112", "spring");
        tdt4112.setCourseName("Programmeringslab for datateknologi");
        second.add(tdt4112);

        Course tfe4101 = new Course("TFE4101", "spring");
        tfe4101.setCourseName("Krets- og digitalteknikk");
        tfe4101.setExamDate("25/05/2017");
        second.add(tfe4101);

        Course tma4115 = new Course("TMA4115", "spring");
        tma4115.setCourseName("Matematikk 3");
        tma4115.setExamDate("06/06/2017");
        second.add(tma4115);

        plan.add(first);
        plan.add(second);

        return plan;
    }

}
