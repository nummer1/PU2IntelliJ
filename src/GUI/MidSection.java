package GUI;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import Algorithm.Course;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by andreaswilhelmflatt on 20.02.2017.
 */
public class MidSection {

    private static GridPane coursePlan = new GridPane();
    private int semesterCount = -1;
    private int count = 0;
    private static ArrayList<ArrayList<Course>> courses = new ArrayList<ArrayList<Course>>();

    public static GridPane getCoursePlan () {
        return coursePlan;
    } // Returns courseplan.

    public void resetCounts() { // Resets counts.
        semesterCount = -1;
        count = 0;
    }

    public GridPane generateMidSection(String from, String to) { // Initializes GridPane and adds courses.
        makeBasicGridPane();

        ArrayList<ArrayList<Algorithm.Course>> firstYear = Algorithm.Selector.get_first_year();

        //TEST
        for (int i = 0; i < 5; i++) {
            addTestList(firstYear);
        }

        //SLUTT TEST

        for (int i = 0; i < 5; i++) {
            generateCoursePlan(firstYear);
        }
        return coursePlan;
    }

    private void generateCoursePlan(ArrayList<ArrayList<Algorithm.Course>> firstYear) {
        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }
    }


    //DENNE FJERNES NÅR SELECTOR FUNGERER
    private void addTestList(ArrayList<ArrayList<Algorithm.Course>> firstYear) {
        for (ArrayList<Course> semester : firstYear) {
            ArrayList<Course> subList = new ArrayList<Course>();
            for (Course course : semester) {
                subList.add(course); // Legger til course i test-arraylist.
            }
            courses.add(subList);
        }
    }

    public void makeBasicGridPane() { // Initializes GridPane.
        coursePlan.getStylesheets().add(MidSection.class.getResource("stylesheets.css").toExternalForm());
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(10);
        coursePlan.setHgap(10);
    }

    public void addCourse(Course course) { // Adds a course to the courseplan.
        if (count == 0) { // Checks if it needs to start a new semester.
            semesterCount++;
            Label semesterLabel = new Label("Semester " + Integer.toString(semesterCount + 1));
            semesterLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            GridPane.setHalignment(semesterLabel, HPos.CENTER); // Center Semester-Label.

            if (semesterCount < courses.size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
                GridPane.setConstraints(semesterLabel, semesterCount, 0);
            }
            else {
                GridPane.setConstraints(semesterLabel, semesterCount - courses.size() / 2, 5);
            }
            coursePlan.getChildren().add(semesterLabel);
        }

        TextArea fag = new TextArea(course.getCourse_id() + "\n" + course.getCourse_name() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);
        DragAndDrop.initializeDragAndDrop(fag);
        OnClickedColorCode.initializeOnClickedColorCode(fag);
        fag.setWrapText(true); // Forces newline if the text use more width than the textbox is given.

        // if (semesterCount < Algorithm.Selector.get_first_year().size() / 2;

        if (semesterCount < courses.size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
            GridPane.setConstraints(fag, semesterCount, count + 1);
        }
        else {
            GridPane.setConstraints(fag, semesterCount - courses.size() / 2, count + 6);
        }
        count++;
        count = count % 4; // Used to make each semester consist of 4 courses.
        coursePlan.getChildren().add(fag);
    }

    public static void colorCompleteSliderCourses(double sliderValue) { // Slider's functionality. Colors courses based on finished semesters.
        int count = 0;
        for (double semester = 0; semester < sliderValue * 2; semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("completed-courses");
                count++;
            }
        }

        for (double semester = sliderValue * 2; semester < courses.size(); semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("all-courses");
                count++;
                if (count == 51) {
                    break;
                }
            }
        }
    }
}
