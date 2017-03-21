
import Algorithm.*;
import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by havardbjornoy on 08/03/2017.
 *  **/
public class switchMajorTest extends TestCase {

    private StudyPlan madePlan;
    private Selector sel = new Selector();
    private DbCom db = new DbCom();
    private Collection<Course> finishedCourses;
    private Collection<Course> neededCourses;
    private StudyPlan shouldPlan;
    private String switchToMajor;
    private String season;

    @Test
    public void testArray() {
        //test1
        setup();
        switchToMajor= "MTDT";
        season = "spring";
        finishedCourses = addToCollection(new String[] {"TDT4110","TMA4100","TMA4140","TTM4175"}); //ITGK, Matte1, DiskMat, KTN
        Semester a = addToSemester(new String[] {"TDT4100", "TDT4112", "TFE4101", "TMA4115"}); //Java, progLab, krets, matte3
        Semester b = addToSemester(new String[] {"EXPH0004", "TDT4113", "TDT4120", "TDT4160"}); //Exphil(1.) PLAB2, AlgDat, DigDat (utsetter statistikk tror jeg er riktig men kanskje ikke lage så strengt?)
        StudyPlan shouldPlan = makeStudyplan("one regular semester of Comtech to CS", new Semester[] {a, b});
        testStudyPlan(finishedCourses, switchToMajor, season, shouldPlan);


        //test2

    }

    public void setup() {
        madePlan = null;
        finishedCourses = null;
        neededCourses = null;
        shouldPlan = null;
        switchToMajor = null;
        season = null;
    }

    @Ignore
    public void testStudyPlan(Collection finishedCourses, String switchToMajor, String season, StudyPlan shouldPlan) {
        madePlan = sel.switch_major(finishedCourses, switchToMajor, season);

        // checking if the length of each semester is the same length
        try {
            assertEquals("There is a different amount of courses in the plan made. There are " + madePlan.getSemester(i).getCourses().size() + " courses, not " + shouldPlan.getSemester(i).getCourses().size() + "courses like it should be.", madePlan.getSemester(1).getCourses().size(), shouldPlan.getSemester(1).getCourses().size());
            assertEquals(madePlan.getSemester(2).getCourses().size(), shouldPlan.getSemester(2).getCourses().size());
        } catch (AssertionFailedError a) {
            a.getMessage();
        }

        // i<2 because we are just checking the 2 first semester if there are any differences
        int i = 0
        while (i < 2) {
            i++;
            Collection<Course> madeCourses = madePlan.getSemester(i).getCourses();
            Collection<Course> shouldCourses = shouldPlan.getSemester(i).getCourses();
            Collection<Course> missingCourses = shouldCourses;
            Collection<Course> wrongCourses = madeCourses;
            missingCourses.removeAll(madeCourses);
            wrongCourses.removeAll(shouldCourses);

            try {
                String msg = "There is a different amount of courses in the plan made. There are " + madeCourses.size() +
                        " courses, not " + shouldCourses.size() + "courses like it should be.";
                assertEquals(msg, madeCourses.size(), shouldCourses.size());

                msg = "The studyplan should contain: " + shouldCourses + " and NOT: " + missingCourses;
                assertEquals(msg, madeCourses, shouldCourses);

            } catch (AssertionFailedError a) {
                a.getMessage();
            }

        }
    }

    public Collection<Course> addToCollection(String[] args) {
        Collection<Course> c = new ArrayList();
        if (args.length < 1) {
            return c;
        }
        for (int i = 0; i < args.length; i++) {
            Course course = db.getCourse(args[i]);
            finishedCourses.add(course);
        }
        return finishedCourses;
    }

    public Semester addToSemester(String[] courses) {
        Semester semester = new Semester();
        for (int i = 0; i < courses.length; i++) {
            Course course = db.getCourse(courses[i]);
            semester.addCourse(course);
        }
        return semester;
    }

    public StudyPlan makeStudyplan(String name, Semester[] semesters) {
        StudyPlan shouldPlan = new StudyPlan(name);
        shouldPlan.setMajor(switchToMajor);
        for (int i = 0; i < semesters.length; i++) {
            Semester semester = semesters[i];
            if (Math.floorMod(i,2) == 0 && season == "spring") {
                semester.setSpring();
            } else if (Math.floorMod(i,2) == 1 && season == "autumn") {
                semester.setSpring();
            } else {
                semester.setAutumn();
            }

            shouldPlan.addSemester(semester);
        }
        return shouldPlan;
    }
}
