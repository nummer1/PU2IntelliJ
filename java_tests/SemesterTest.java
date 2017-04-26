import Algorithm.*;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Erlend on 05.04.2017.
 */
public class SemesterTest extends TestCase {

    private Semester sem;
    private Course c;

    private void setup() {
        this.sem = new Semester();
    }

    private void fillWithCourses() {
        while(!this.sem.isFilled()) {
            c = new Course("someCourse", "agile");
            this.sem.addCourse(c);
        }
    }

    public void testStudypoints() {
        setup();
        assertEquals(this.sem.getStudypoints(), 0.0);
        fillWithCourses();
        assertEquals(this.sem.getStudypoints(), 30.0);
    }

    public void testAddCourseList() {
        setup();
        ArrayList<Course> ac = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            c = new Course("someCourse", "agile");
            ac.add(c);
        }
        this.sem.addCourseList(ac);
        assertEquals(ac, this.sem.getCourses());
        assertEquals(this.sem.getStudypoints(), 30.0);
    }

    public void testFillWithElectives() {
        setup();
        assertEquals(this.sem.getStudypoints(), 0.0);
        this.sem.fillWithElectives(1);
        assertEquals(this.sem.getStudypoints(), 30.0);
        assertTrue(this.sem.getCourses().contains(new Course("valg1", "agile")));
    }

    public void testSeasons() {
        setup();
        this.sem.setAutumn();
        assertTrue(this.sem.getSeason().equals("autumn"));
        this.sem.setSpring();
        assertTrue(this.sem.getSeason().equals("spring"));
    }

}
