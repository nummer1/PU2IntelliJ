/**
 * Created by Erlend on 13.02.2017.
 */

import junit.framework.TestCase;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;

public class CourseTest extends TestCase{

    private Course tdt4100;
    private Date dt = new Date();
    private Calendar c = Calendar.getInstance();


    private void setup() {
        this.tdt4100 = new Course("tdt4100");
        tdt4100.setDescription("This is a description");
        tdt4100.setDifficulty(5);
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        tdt4100.setExam_date(dt);
        tdt4100.setFaculty("IME");
    }

    public void testSetup() {
        setup();
        assertNotNull(tdt4100.getCourse_id());
        assertNotNull(tdt4100.getDescription());
        assertNotNull(tdt4100.getFaculty());
        assertNotNull(tdt4100.getDifficulty());
        assertNotNull(tdt4100.getExam_date());
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
        }
        try {
            tdt4100.setDifficulty(14);
        } catch (IllegalArgumentException e) {
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
}
