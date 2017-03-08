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

    /*public static void initialize() {
        coursePlan.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = coursePlan.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString("Hello!");
                db.setContent(content);
                System.out.println("Drag detected");
                event.consume();
            }
        });


        coursePlan.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                System.out.println("Drag over detected");
                event.consume();
            }
        });

        coursePlan.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                System.out.println("Drop detected");
                event.consume();
            }
        });
    }
    */

    public GridPane getCoursePlan () {
        return coursePlan;
    }

    public void resetCounts() {
        semesterCount = -1;
        count = 0;
    }

    public GridPane generateMidSection(String from, String to) {
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

    public void makeBasicGridPane() {
        coursePlan.getStylesheets().add(MidSection.class.getResource("stylesheets.css").toExternalForm());
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(10);
        coursePlan.setHgap(10);
    }

    public void addCourse(Course course) {
        if (count == 0) {
            semesterCount++;
            Label semesterLabel = new Label("Semester " + Integer.toString(semesterCount + 1));
            semesterLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            GridPane.setHalignment(semesterLabel, HPos.CENTER);
            //SENTRER ALLE SEMESTERLABELS. GOOGLE
            if (semesterCount < 5) {
                GridPane.setConstraints(semesterLabel, semesterCount, 0);
            }
            else {
                GridPane.setConstraints(semesterLabel, semesterCount - 5, 5);
            }
            coursePlan.getChildren().add(semesterLabel);
        }
        //TextField fag = new TextField(course);

        TextArea fag = new TextArea(course.getCourse_id() + "\n" + course.getCourse_name() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);
        fag.setWrapText(true); // TVINGER NEWLINE DERSOM TEKSTEN BRUKER STØRRE BREDDE EN BOXEN ER TILDELT
        initializeOnClickedListener(fag);
        //fag.setAlignment(Pos.CENTER);
        if (semesterCount <5) {
            GridPane.setConstraints(fag, semesterCount, count + 1);
        }
        else {
            GridPane.setConstraints(fag, semesterCount - 5, count + 6);
        }
        System.out.println(semesterCount);
        count++;
        count = count % 4;
        coursePlan.getChildren().add(fag);
    }

    public void initializeOnClickedListener(TextArea fag) {
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

    public static void colorCompleteSliderCourses(double sliderValue) {
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
