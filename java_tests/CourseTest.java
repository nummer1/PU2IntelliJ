/**
 * Created by Erlend on 13.02.2017.
 */

import Algorithm.Course;
import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CourseTest extends TestCase{

    private Course tdt4100;
    private Date dt = new Date();
    private Calendar c = Calendar.getInstance();


    private void setup() {
        this.tdt4100 = new Course("TDT4100", "agile");
        tdt4100.setCourseName("Objektorientert programmering");
        tdt4100.setDescription("This is a description");
        tdt4100.setDifficulty(5);
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        tdt4100.setExamDate("30/12/2017");
        tdt4100.setFaculty("IME");
        tdt4100.setURL("https://www.ntnu.no/studier/emner/TDT4100");
    }

    public void testSetup() {
        setup();
        assertNotNull(tdt4100.getCourseName());
        assertNotNull(tdt4100.getCourseId());
        assertNotNull(tdt4100.getDescription());
        assertNotNull(tdt4100.getFaculty());
        assertNotNull(tdt4100.getDifficulty());
        assertNotNull(tdt4100.getExamDate());
        assertNotNull(tdt4100.getPrintable_date());
        assertNotNull(tdt4100.getURL());
    }

    public void testCourseId() {
        setup();
        tdt4100.setCourseId("TMA4100");
        assertEquals(tdt4100.getCourseId(), "TMA4100");
        try {
            tdt4100.setCourseId("Objekt-orientert programmering");
            Assert.fail("Only chars is not allowed");
        } catch (IllegalArgumentException e) {
            //An error is expected here
        }
        try {
            tdt4100.setCourseId("235781487378141");
            Assert.fail("Only numbers is not allowed");
        } catch (IllegalArgumentException e) {
            //An error is expected here
        }
        try {
            tdt4100.setCourseId("T4125");
            Assert.fail("One char and 4 numbers is not allowed");
        } catch (IllegalArgumentException e) {
            //An error is expected here
        }
    }

    public void testDescription() {
        setup();
        assertEquals(tdt4100.getDescription(), "This is a description");
        String nonsense = "Thisissis 124124 asfsaas";
        tdt4100.setDescription(nonsense);
        assertEquals(tdt4100.getDescription(), nonsense);
    }

    public void testDifficulty() {
        setup();
        assertEquals(tdt4100.getDifficulty(), 5);
        try {
            tdt4100.setDifficulty(-1);
            Assert.fail("It should throw IllegalArgument");
        } catch (IllegalArgumentException e) {
            //An error is expected here
        }
        try {
            tdt4100.setDifficulty(14);
        } catch (IllegalArgumentException e) {
            //An error is expected here
        }
        tdt4100.setDifficulty(7);
        assertEquals(tdt4100.getDifficulty(), 7);
    }

    public void testFaculty() {
        setup();
        assertEquals(tdt4100.getFaculty(), "IME");
        try {
            tdt4100.setFaculty("12313142");
            Assert.fail("It should throw IllegalArgument");
        } catch (IllegalArgumentException e) {
        }
        tdt4100.setFaculty("IDI");
        assertEquals(tdt4100.getFaculty(), "IDI");
    }

    public void testSeason() {
        setup();
        assertEquals(true, this.tdt4100.isAgile());
        assertEquals(true, this.tdt4100.isAutumn());
        assertEquals(true, this.tdt4100.isSpring());
        Course c = new Course("blah", "spring");
        assertTrue(c.isSpring());
        assertFalse(c.isAutumn());
        c = new Course("blah", "autumn");
        assertFalse(c.isSpring());
        assertTrue(c.isAutumn());
    }

    public void testScore() {
        setup();
        assertEquals(0.0, this.tdt4100.getScore());
        this.tdt4100.setScore(200);
        assertEquals(200.0, this.tdt4100.getScore());
    }

    public void testDependencies() {
        setup();
        this.tdt4100.addDependency("aCourse");
        ArrayList<String> depArray = this.tdt4100.getDependencies();
        assertTrue(depArray.contains("aCourse"));
        this.tdt4100.removeDependency("aCourse");
        depArray = this.tdt4100.getDependencies();
        assertTrue(depArray.isEmpty());
    }
}
