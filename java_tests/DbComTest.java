import Algorithm.Course;
import Algorithm.DbCom;
import Algorithm.StudyPlan;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Erlend on 06.04.2017.
 */
public class DbComTest extends TestCase {

    private DbCom dbCom;

    private void setup() {
        this.dbCom = new DbCom();
    }

    public void testSetup() {
        try {
            setup();
        } catch (IllegalStateException e) {
            fail("Could not connect to the database");
        }
    }

    public void testGetStudyCode() {
        setup();
        String s = this.dbCom.getStudyCode("Datateknologi");
        assertEquals("MTDT", s);
        s = this.dbCom.getStudyCode("This is nonsense");
        assertEquals(null, s);
    }

    public void testGetCoursesFromMajor() {
        setup();
        StudyPlan s = this.dbCom.getCoursesFromMajor("MTDT");
        ArrayList<Course> sa = s.getCourses();
        assertNotNull(sa);
    }

}
