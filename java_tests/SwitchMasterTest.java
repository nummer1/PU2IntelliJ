import Algorithm.*;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Erlend on 08.03.2017.
 */
public class SwitchMasterTest extends TestCase{

    StudyPlan expected = new StudyPlan("Custom studyplan");
    Semester sem = new Semester("autumn");
    StudyPlan actual = new StudyPlan("Custom studyplan");
    StudyPlan a;
    StudyPlan b;

    public void setup() {
        StudyPlan a = new StudyPlan("a");
        StudyPlan b = new StudyPlan("b");

        for (int i=8; i<10; i++) {
            Course c = new Course(Integer.toString(i), "autumn");
            this.sem.addCourse(c);
        }
        this.expected.addSemester(this.sem, 0);

        Semester asem1 = new Semester("autumn");
        Semester asem2 = new Semester("spring");
        Semester bsem1 = new Semester("autumn");
        Semester bsem2 = new Semester("spring");

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i), "autumn");
            asem1.addCourse(c);
            bsem1.addCourse(c);
        }

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i+4), "autumn");
            asem2.addCourse(c);
        }

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i+6), "autumn");
            bsem2.addCourse(c);
        }

        a.addSemester(asem1, 0);
        a.addSemester(asem2, 0);

        b.addSemester(bsem1, 0);
        b.addSemester(bsem2, 0);

        this.a = a;
        this.b = b;
    }

    public void testSwitchMajor() {
        setup();
        Selector sel = new Selector();
        ArrayList<Course> from = new ArrayList<>(a.getCourses());
        ArrayList<Course> to = new ArrayList<>(b.getCourses());
        String toMajor = "MTDT";

        //this.actual = sel.switchMajor(to, toMajor, "spring", 0)

        boolean correct = this.actual.getCourses().containsAll(this.expected.getCourses());

        assertTrue(correct);
    }


}