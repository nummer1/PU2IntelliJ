import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

/**
 * Created by andreaswilhelmflatt on 20.02.2017.
 */
public class MidSection {

    private static GridPane coursePlan = new GridPane();
    private static int semesterCount = -1;
    private static int count = 0;

    public static GridPane getCoursePlan () {
        return coursePlan;
    }

    public static void resetCounts() {
        semesterCount = -1;
        count = 0;
    }

    public static GridPane generateMidSection(String from, String to) {
        makeBasicGridPane();

        ArrayList<ArrayList<Course>> firstYear = Selector.get_first_year();

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }
        return coursePlan;
    }

    public static void makeBasicGridPane() {
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(10);
        coursePlan.setHgap(10);
    }

    public static void addCourse(Course course) {
        if (count == 0) {
            semesterCount++;
            Label semesterLabel = new Label("Semester" + Integer.toString(semesterCount + 1));
            //SENTRER ALLE SEMESTERLABELS. GOOGLE
            GridPane.setConstraints(semesterLabel, semesterCount, 0);
            coursePlan.getChildren().add(semesterLabel);
        }
        TextArea fag = new TextArea(course.getCourse_id() + "\n" + course.getCourse_name() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.setEditable(false);
        GridPane.setConstraints(fag, semesterCount, count + 1);
        count++;
        count = count % 4;
        coursePlan.getChildren().add(fag);
    }
}
