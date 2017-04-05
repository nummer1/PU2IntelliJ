/*

import Algorithm.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;

*
 * Created by havardbjornoy on 08/03/2017.


public class switch_majorTest extends TestCase {

    ArrayList<ArrayList<Course>> madePlan;
    Selector sel = new Selector();
    DbCom db = new DbCom();

    public void combinations() {
        Collection<Course> finishedCourses = new ArrayList<Course>() {{
            add(db.getCourse("TDT4110"));
            add(db.getCourse("TMA4100"));
            add(db.getCourse("TMA4140"));
            add(db.getCourse("TTM4175"));
        }};
        String switchToMajor = "MTDT";

        shouldPlan1 = new StudyPlan("Halvt år Comtech til CS");
        shouldPlans = [shouldPlan1];
    }

    public void setup(Collection from, Collection to int semesters) {

        madePlan = sel.switch_major(Collection<Course> from, Collection≤Course> to, int semesters)
    }

    public void test_array(Collection from, Collection to int semesters, StudyPlan shouldPlan) {
        setup(Collection from, Collection to, int semesters);
        assertEquals(this.madePlan, shouldPlan); // lage det slik at studyplan kan sjekke om semester-collection er like


        //ArrayList<Course> some_semester = this.plan.get(0);
        //Course some_course = some_semester.get(2);
        //System.out.println(some_course.getCourse_id() + " " + some_course.getCourse_name() + " " + some_course.getExam_date());
        //assertEquals("TMA4140", some_course.getCourse_id());
        //assertEquals("Diskret matematikk", some_course.getCourse_name());
    }

}
*/
