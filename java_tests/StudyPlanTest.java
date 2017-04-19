import Algorithm.Course;
import Algorithm.Semester;
import Algorithm.StudyPlan;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Erlend on 05.04.2017.
 */
public class StudyPlanTest extends TestCase {

    private StudyPlan studyPlan;

    private void setup() {
        this.studyPlan = new StudyPlan("Custom Studyplan");
    }

    public void testAddSemester() {
        setup();
        Semester sem = new Semester("autumn");
        this.studyPlan.addSemester(sem, 1);
        assertEquals(sem, this.studyPlan.getSemester(1));
        Collection<Semester> semArray = this.studyPlan.getSemesters();
        assertTrue(semArray.contains(sem));
    }

    public void testGetCourses() {
        setup();
        Semester sem = new Semester("autumn");
        Course c = new Course("test", "autumn");
        sem.addCourse(c);
        this.studyPlan.addSemester(sem, 1);
        ArrayList<Course> ac1 = this.studyPlan.getCourses();
        ArrayList<Course> ac2 = new ArrayList<>();
        ac2.add(c);
        assertEquals(ac2, ac1);
    }

    public void testScore() {
        setup();
        this.studyPlan.setScore(1001);
        assertEquals(1001, this.studyPlan.getScore());
    }

    public void testMajor() {
        setup();
        this.studyPlan.setMajor("MTDT");
        assertEquals("MTDT", this.studyPlan.getMajor());
    }

}
