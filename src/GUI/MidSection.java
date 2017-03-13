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

    public void initializeDragAndDrop(TextArea fag) {
        fag.setOnDragDetected(event -> {
            System.out.println("onDragDetected");

            String sourceText = fag.getText();

            Dragboard db = fag.startDragAndDrop(TransferMode.COPY_OR_MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(sourceText);

            db.setContent(content);
            event.consume();
        });

        fag.setOnDragOver(event -> {
            // If drag board has a string, let the event know that
            // the target accepts copy and move transfer modes
            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString())
            {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        fag.setOnDragDropped(event -> {
            // Transfer the data to the target
            Dragboard dragboard = event.getDragboard();

            if(event.getGestureSource() == event.getGestureTarget()) {return;}

            if (dragboard.hasString())
            {
                TextArea targetFag = new TextArea();
                TextArea sourceFag = new TextArea();

                int targetIndex = -1;
                int sourceIndex = -1;

                for (int i = 0; i < coursePlan.getChildren().size(); i++) {
                    if (event.getGestureTarget().equals(coursePlan.getChildren().get(i))) {
                        targetFag = (TextArea) coursePlan.getChildren().get(i);
                        targetIndex = i;
                    }
                    if (event.getGestureSource().equals(coursePlan.getChildren().get(i))) {
                        sourceFag = (TextArea) coursePlan.getChildren().get(i);
                        sourceIndex = i;
                    }
                }

                String targetText = targetFag.getText();
                String sourceText = sourceFag.getText();

                ((TextArea) coursePlan.getChildren().get(targetIndex)).setText(dragboard.getString()); // Set target text.
                ((TextArea) coursePlan.getChildren().get(sourceIndex)).setText(targetText); // Set source text.
                System.out.println(targetFag.getText());

                // Data transfer is successful
                event.setDropCompleted(true);
            }
            else
            {
                // Data transfer is not successful
                event.setDropCompleted(false);
            }

            event.consume();
        });

    }


    public GridPane getCoursePlan () {
        return coursePlan;
    } // Returns courseplan.

    public void resetCounts() { // Resets counts.
        semesterCount = -1;
        count = 0;
    }

    public GridPane generateMidSection(String from, String to) { // Initializes GridPane and adds courses.
        makeBasicGridPane();

        ArrayList<ArrayList<Algorithm.Course>> firstYear = Algorithm.Selector.get_first_year();

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }

        for (ArrayList<Course> semester : firstYear) {
            for (Course course : semester) {
                addCourse(course);
            }
        }
        return coursePlan;
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

            if (semesterCount < 5) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
                GridPane.setConstraints(semesterLabel, semesterCount, 0);
            }
            else {
                GridPane.setConstraints(semesterLabel, semesterCount - 5, 5);
            }
            coursePlan.getChildren().add(semesterLabel);
        }

        TextArea fag = new TextArea(course.getCourse_id() + "\n" + course.getCourse_name() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);
        initializeDragAndDrop(fag);
        fag.setWrapText(true); // Forces newline if the text use more width than the textbox is given.
        initializeOnClickedListener(fag); // Adds listener to add color-coding functionality.
        if (semesterCount <5) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
            GridPane.setConstraints(fag, semesterCount, count + 1);
        }
        else {
            GridPane.setConstraints(fag, semesterCount - 5, count + 6);
        }
        count++;
        count = count % 4; // Used to make each semester consist of 4 courses.
        coursePlan.getChildren().add(fag);
    }

    public void initializeOnClickedListener(TextArea fag) { // Listens for mouse-clicks on one of the courses. Toggles color-coding. NOT YET FUNCTIONAL
        fag.onMouseClickedProperty().addListener(text -> {
            if (fag.getStyleClass().get(0).equals("completed-courses")) {
                fag.getStyleClass().remove("completed-courses");
                fag.getStyleClass().add("all-courses");
            }
            else {
                fag.getStyleClass().remove("all-courses");
                fag.getStyleClass().add("completed-courses");
            }
        });
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

        for (double semester = sliderValue * 2; semester < 10; semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("all-courses");
                count++;
            }
        }
    }
}
