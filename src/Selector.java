import java.util.Dictionary;
import com.google.gson.*;

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

    //Takes in information gotten from the database and puts it into a Course object.
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

}
