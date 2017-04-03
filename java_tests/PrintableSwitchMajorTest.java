import Algorithm.DbCom;
import Algorithm.Selector;
import Algorithm.StudyPlan;


import java.util.ArrayList;

/**
 * Created by Erlend on 20.03.2017.
 */
public class PrintableSwitchMajorTest {

    StudyPlan from;
    StudyPlan to;
    DbCom db = new DbCom();
    Selector sel = new Selector();

    private PrintableSwitchMajorTest () {
        this.from = db.getCoursesFromMajor("MTKOM");
        this.to = db.getCoursesFromMajor("MTDT");
    }

    private void printFrom() {
        System.out.println(this.from);
    }

    private void printTo() {
        System.out.println(this.to);
    }

    private void printSwitch() {
        //StudyPlan newPlan = sel.switchMajor("MTDT", "autumn", 2);
        //System.out.println(newPlan);
    }

    public static void main(String[] args) {
        PrintableSwitchMajorTest m = new PrintableSwitchMajorTest();
        m.printFrom();
        m.printTo();
        m.printSwitch();
    }

}
