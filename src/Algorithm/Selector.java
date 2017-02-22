package Algorithm;

import java.util.ArrayList;
import java.util.Dictionary;

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
    private void switch_major(String from, String to, int semesters) {
        //TODO
    }

    //Takes in information gotten from the database and puts it into a Algorithm.Course object.
    private Course convert_to_course(Dictionary course) {
        //TODO
        return new Course("TDT4100");
    }

    //Gets courses from the database.
    private void get_course_from_db(String course) {
        //TODO
    }

    //Gets information about the different plans for different majors.
    private void get_plan_from_db(String plan) {
        //TODO
    }

    //This function is only for demonstration purposes
    //Do not call this function unless you only want a static list over the first
    //8 courses in mtdt :)
    public static ArrayList<ArrayList<Course>> get_first_year() {
        ArrayList<ArrayList<Course>> plan = new ArrayList<>();
        ArrayList<Course> first = new ArrayList<>();
        ArrayList<Course> second = new ArrayList<>();

        Course tdt4110 = new Course("TDT4110");
        tdt4110.setCourse_name("Informasjonsteknologi, grunnkurs");
        tdt4110.setExam_date("12/12/2017");
        first.add(tdt4110);

        Course tma4100 = new Course("TMA4100");
        tma4100.setCourse_name("Matematikk 1");
        tma4100.setExam_date("08/12/2017");
        first.add(tma4100);

        Course tma4140 = new Course("TMA4140");
        tma4140.setCourse_name("Diskret matematikk");
        tma4140.setExam_date("02/12/2017");
        first.add(tma4140);

        Course exph0004 = new Course("EXPH0004");
        exph0004.setCourse_name("Examen philosophicum for naturvitenskap og teknologi");
        exph0004.setExam_date("27/11/2017");
        first.add(exph0004);

        Course tdt4100 = new Course("TDT4100");
        tdt4100.setCourse_name("Objektorientert programmering");
        tdt4100.setExam_date("15/05/2017");
        second.add(tdt4100);

        Course tdt4112 = new Course("TDT4112");
        tdt4112.setCourse_name("Programmeringslab for datateknologi");
        second.add(tdt4112);

        Course tfe4101 = new Course("TFE4101");
        tfe4101.setCourse_name("Krets- og digitalteknikk");
        tfe4101.setExam_date("25/05/2017");
        second.add(tfe4101);

        Course tma4115 = new Course("TMA4115");
        tma4115.setCourse_name("Matematikk 3");
        tma4115.setExam_date("06/06/2017");
        second.add(tma4115);

        plan.add(first);
        plan.add(second);

        return plan;
    }

}