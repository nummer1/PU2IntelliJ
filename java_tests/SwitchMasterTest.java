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
    DbCom com = new DbCom();

    public void setup() {
        StudyPlan a = new StudyPlan("a");
        StudyPlan b = new StudyPlan("b");

        for (int i=6; i==9; i++) {
            Course c = new Course(Integer.toString(i), "autumn");
            sem.addCourse(c);
        }
        expected.addSemester(sem);

        Semester asem1 = new Semester("autumn");
        Semester bsem1 = new Semester("autumn");

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i), "autumn");
            asem1.addCourse(c);
            bsem1.addCourse(c);
        }

        Semester asem2 = new Semester("spring");

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i+4), "autumn");
            asem2.addCourse(c);
        }

        Semester bsem2 = new Semester("spring");

        for(int i=0; i<4; i++) {
            Course c = new Course(Integer.toString(i+6), "autumn");
            bsem1.addCourse(c);
        }

        a.addSemester(asem1);
        a.addSemester(asem2);

        b.addSemester(bsem1);
        b.addSemester(bsem2);

        this.a = a;
        this.b = b;
    }

    public void testSwitchMajor() {
        Selector sel = new Selector();
        ArrayList<Course> from = new ArrayList<>(a.getCourses());
        ArrayList<Course> to = new ArrayList<>(b.getCourses());

        actual = sel.switch_major(from, to, 1);

        
    }


}
