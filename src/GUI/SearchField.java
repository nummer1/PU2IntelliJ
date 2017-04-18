package GUI;

import Algorithm.Course;
import Algorithm.DbCom;
import Algorithm.Semester;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SearchField {

    private static ComboBox searchField = new ComboBox();

    public static ComboBox getSearchField() {
        return searchField;
    }

    public static void initializeSearchField() { // Initializes search-field.
        searchField.setPromptText("Search for courses.");
        searchField.setVisible(false);

        new AutoCompleteComboBoxListener<>(searchField);

        addSearchAbleCourses();

        searchField.setOnMouseClicked(event -> {
            if (searchField.getSelectionModel().isEmpty()) {
                return;
            }
            else {
                String courseCode = searchField.getSelectionModel().getSelectedItem().toString().split(":")[0];
                DbCom db = new DbCom();
                Course course = db.getCourseSingle(courseCode);
                ArrayList<ArrayList<Course>> courses = MidSection.getCourses();

                for (ArrayList<Course> semester : courses) {
                    if (semester.contains(course)) {return;}
                }

                int count = 0;
                int semesterCount = 0;

                for (ArrayList<Course> semester : courses) {
                    if (semester.size() == 4) {
                        semesterCount++;
                        continue;
                    }
                    else {
                        count += semester.size();
                        MidSection.addCustomCourse(course, semesterCount, count + 1);
                        MidSection.getCourses().get(semesterCount).add(course);
                        searchField.getItems().remove(course.getCourseId() + ":" + course.getCourseName());
                        return;
                    }
                }
            }
        });
    }

    private static void addSearchAbleCourses() {
        searchField.getItems().addAll(new DbCom().getCoursesAsString());
    }
}