import Algorithm.Selector;
import junit.framework.TestCase;
import java.util.ArrayList;
import Algorithm.*;

/**
 * Created by Erlend on 20.02.2017.
 */
public class SelectorFirstYearTest extends TestCase {

    ArrayList<ArrayList<Course>> plan;
    Selector sel = new Selector();

    public void setup() {
        plan = sel.get_first_year();
    }

    public void test_array() {
        setup();
        ArrayList<Course> some_semester = this.plan.get(0);
        Course some_course = some_semester.get(2);
        System.out.println(some_course.getCourseId() + " " + some_course.getCourseName() + " " + some_course.getExamDate());
        assertEquals("TMA4140", some_course.getCourseId());
        assertEquals("Diskret matematikk", some_course.getCourseName());
    }

}
