package GUI;

import Algorithm.Course;
import Algorithm.DbCom;
import Algorithm.Semester;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SearchField {

    private static VBox instructionsSearchFieldAndBtn = new VBox(5);
    private static ComboBox searchField = new ComboBox();

    public static ComboBox getSearchField() {
        return searchField;
    }

    public static VBox getInstructionsSearchFieldAndBtn() {return instructionsSearchFieldAndBtn;}

    public static void initializeSearchField() { // Initializes search-field and instructions.
        instructionsSearchFieldAndBtn.getChildren().clear();
        Label instructions = new Label("Search custom courses");
        instructions.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        Button btn = new Button("Add");
        btn.getStyleClass().add("change-interface-btn");
        btn.setOnAction(event -> {
            if (searchField.getSelectionModel().isEmpty()) { // Add button does nothing if a course is not selected.
                return;
            }
            else {
                String courseCode = searchField.getSelectionModel().getSelectedItem().toString().split(":")[0];
                DbCom db = new DbCom();
                Course course = db.getCourseSingle(courseCode); // Gets the specified course as a Course-object.
                ArrayList<ArrayList<Course>> courses = MidSection.getCourses();

                for (ArrayList<Course> semester : courses) {
                    if (semester.contains(course)) {return;}
                }

                int semesterCount = 0;

                for (ArrayList<Course> semester : courses) {
                    if (semester.size() == 4) {
                        semesterCount++;
                        continue;
                    }
                    else {
                        MidSection.getCourses().get(semesterCount).add(course); // Adds the course to courses-arrayList.
                        searchField.getItems().remove(course.getCourseId() + ":" + course.getCourseName()); // Removes it from Searchfield so it can't be added twice.
                        MidSection.generateCoursePlan(MidSection.getCourses());
                        MidSection.colorCompleteSliderCourses(SemesterSliderAndInstructions.getSlider().getValue());
                        return;
                    }
                }
            }
        });

        searchField.setPromptText("Search for courses.");

        new AutoCompleteComboBoxListener<>(searchField);

        addSearchAbleCourses();

        HBox searchFieldAndBtn = new HBox(2);
        searchFieldAndBtn.setAlignment(Pos.CENTER);
        searchFieldAndBtn.getChildren().addAll(searchField, btn);

        instructionsSearchFieldAndBtn.setVisible(false);
        instructionsSearchFieldAndBtn.getChildren().addAll(instructions, searchFieldAndBtn);
    }

    private static void addSearchAbleCourses() {
        searchField.getItems().addAll(new DbCom().getCoursesAsString());
    }
}