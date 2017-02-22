import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by andreaswilhelmflatt on 20.02.2017.
 */
public class MidSection {

    private static GridPane coursePlan = new GridPane();
    private static int semesterCount = -1;
    private static int count = 0;


    // ORDNE DENNE VHA COURSE-KODEN TIL ERLEND
    /*public void addSemester(ArrayList<Course> semesterArg) {
        VBox semester = new VBox(10);

        //LEGG TIL SELECTOR-KODEN TIL ERLEND HER

        for (Course course : semesterArg) {
            semester.getChildren().add(semesterArg);
        }
        coursePlan.getChildren().add(semester);
    }
    */

    public static GridPane generateMidSection(String from, String to) {
        makeBasicGridPane();

        addExampleCourses();

        return coursePlan;
    }

    public static void makeBasicGridPane() {
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(10);
        coursePlan.setHgap(10);
    }

    public static void addCourse(String course) {
        if (count == 0) {
            semesterCount++;
            Label semesterLabel = new Label("Semester" + Integer.toString(semesterCount + 1));
            //SENTRER ALLE SEMESTERLABELS. GOOGLE
            GridPane.setConstraints(semesterLabel, semesterCount, 0);
            coursePlan.getChildren().add(semesterLabel);
        }
        TextField fag = new TextField(course);
        fag.setEditable(false);
        fag.setAlignment(Pos.CENTER);
        GridPane.setConstraints(fag, semesterCount, count + 1);
        System.out.println(semesterCount);
        count++;
        count = count % 4;
        coursePlan.getChildren().add(fag);
    }

    public static void addExampleCourses() { /// DENNE SLETTES NÅR VI HAR IMPLEMENTERT DATABASEN
        addCourse("TDT4120");
        addCourse("TDT4110");
        addCourse("TDT4100");
        addCourse("TDT2100");
        addCourse("TDT8418");
        addCourse("TDT2344");
        addCourse("TDT4120");
        addCourse("TDT4110");
        addCourse("TDT4100");
        addCourse("TDT2100");
        addCourse("TDT8418");
        addCourse("TDT2344");
        addCourse("TDT4120");
        addCourse("TDT4110");
        addCourse("TDT4100");
        addCourse("TDT2100");
        addCourse("TDT8418");
        addCourse("TDT2344");
        addCourse("TDT4120");
        addCourse("TDT4110");
        addCourse("TDT4100");
        addCourse("TDT2100");
        addCourse("TDT8418");
        addCourse("TDT2344");
    }
}
