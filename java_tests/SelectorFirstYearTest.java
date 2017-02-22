import Algorithm.Selector;
import junit.framework.TestCase;
import java.util.ArrayList;

/**
 * Created by Erlend on 20.02.2017.
 */
public class SelectorFirstYearTest extends TestCase {

    ArrayList<ArrayList<Selector.Course>> plan;
    Selector sel = new Selector();

    public void setup() {
        plan = sel.get_first_year();
    }

    public void test_array() {
        setup();
        ArrayList<Selector.Course> some_semester = this.plan.get(0);
        Selector.Course some_course = some_semester.get(2);
        System.out.println(some_course.getCourse_id() + " " + some_course.getCourse_name() + " " + some_course.getExam_date());
        assertEquals("TMA4140", some_course.getCourse_id());
        assertEquals("Diskret matematikk", some_course.getCourse_name());
    }

}
