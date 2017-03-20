import Algorithm.Course;
import Algorithm.DbCom;
import Algorithm.StudyPlan;

import java.util.ArrayList;

/**
 * Created by Erlend on 20.03.2017.
 */
public class printableSwitchMajorTest {

    private void setup() {

        DbCom db = new DbCom();
        StudyPlan from = db.getCoursesFromMajor("MTKOM");
        StudyPlan to = db.getCoursesFromMajor("MTDT");

    }

}
