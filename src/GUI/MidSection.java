package GUI;

import Algorithm.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by andreaswilhelmflatt on 20.02.2017.
 */
public class MidSection {

    private static GridPane coursePlan = new GridPane();
    private static int semesterCount = -1;
    private static int count = 0;
    private static ArrayList<ArrayList<Course>> courses;
    private static ArrayList<Course> finishedCourses = new ArrayList<>();
    private static ArrayList<Integer> labelIndexes = new ArrayList<>();

    public static GridPane getCoursePlan () {return coursePlan;} // Returns courseplan.

    public int getCount() {return count;}

    public int getSemesterCount() {return semesterCount;}

    public static ArrayList<Integer> getLabelIndexes(){return labelIndexes;}

    public static ArrayList<ArrayList<Course>> getCourses() {return courses;}

    public static void resetCounts() { // Resets counts.
        semesterCount = -1;
        count = 0;
    }

    private static Collection<String> getStudyPlanCoursesAsString() {
        Collection<String> coursesAsString = new ArrayList<>();

        for (ArrayList<Course> semester : courses) {
            for (Course course : semester) {
                coursesAsString.add(course.getCourseId() + ":" + course.getCourseName());
            }
        }
        return coursesAsString;
    }

    public GridPane showAllCoursesFrom(String from) {
        makeBasicGridPane();

        DbCom dbcom = new DbCom();

        String fromStudyCode = dbcom.getStudyCode(from);

        StudyPlan studyPlan = dbcom.getCoursesFromMajor(fromStudyCode);

        Collection<Semester> semesters = studyPlan.getSemesters();

        courses = new ArrayList<>();

        for (Semester semester : semesters) {
            ArrayList<Course> sem = new ArrayList<>(semester.getCourses());
            courses.add(sem);
        }

        SearchField.getSearchField().getItems().removeAll(MidSection.getStudyPlanCoursesAsString());

        generateCoursePlan(courses);

        return coursePlan;
    }

    public static TextArea getCourseTextArea(int index) {
        return ((TextArea)((HBox) coursePlan.getChildren().get(index)).getChildren().get(0));
    }

    public static void updateLabelIndexes() {
        labelIndexes.clear();
        int counter = 0;

        for (ArrayList<Course> semester : courses) {
            labelIndexes.add(counter);
            counter++;

            for (Course course : semester) {
                counter++;
            }
        }
    }

    public static Button getCourseButton(int index) {
        return ((Button) ((HBox) coursePlan.getChildren().get(index)).getChildren().get(1));
    }

    public GridPane generateMidSection(String from, String to, int finishedSemesters) { // Initializes GridPane and adds courses.
        makeBasicGridPane();

        Selector sel = new Selector();

        DbCom dbcom = new DbCom();

        String fromStudyCode = dbcom.getStudyCode(from);
        String toStudyCode = dbcom.getStudyCode(to);
        ArrayList<Course> finishedCourses = new ArrayList<>();
        finishedCourses.addAll(getCompletedCourses());
        StudyPlan studyplan = sel.switchMajor(finishedCourses, toStudyCode, "autumn", finishedSemesters);
        Collection<Semester> semesters = studyplan.getSemesters();

        courses.clear();

        for (Semester semester : semesters) {
            ArrayList<Course> sem = new ArrayList<>(semester.getCourses());
            courses.add(sem);
        }

        getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
        resetCounts(); // Reset counts for indexing courses.

        generateCoursePlan(courses);
        return coursePlan;
    }

    private void generateCoursePlan(ArrayList<ArrayList<Course>> courses) {
        labelIndexes.clear();
        int labelCount = 0;

        for (ArrayList<Course> semester : courses) {
            labelIndexes.add(labelCount);
            labelCount++;

            count = 0;

            semesterCount++;
            Label semesterLabel = new Label("Semester " + Integer.toString(semesterCount + 1));

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            semesterLabel.setMinWidth((screenSize.getWidth() / (courses.size()/2)) - 10);
            semesterLabel.setMaxWidth((screenSize.getWidth() / (courses.size()/2)) - 10);
            semesterLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
            GridPane.setHalignment(semesterLabel, HPos.CENTER); // Center Semester-Label.


            if (semesterCount < getCourses().size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
                GridPane.setConstraints(semesterLabel, semesterCount, 0);
            }
            else {
                GridPane.setConstraints(semesterLabel, semesterCount - getCourses().size() / 2, 5);
            }
            coursePlan.getChildren().add(semesterLabel);

            for (Course course : semester) {
                addCourse(course);
                labelCount++;
            }
        }
    }

    private void makeBasicGridPane() { // Initializes GridPane.
        coursePlan.getStylesheets().add(MidSection.class.getResource("stylesheets.css").toExternalForm());
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(10);
        coursePlan.setHgap(3);
    }

    public static void addCourse(Course course) { // Adds a course to the courseplan.
        HBox fagAndButton = new HBox(0);

        RemoveCourseBtn btn = new RemoveCourseBtn(coursePlan.getChildren().size());

        TextArea fag = new TextArea(course.getCourseId() + "\n" + course.getCourseName() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);

        double size = 12;
        fag.setFont(Font.font(size));

        DragAndDrop.initializeDragAndDrop(fag);
        OnClickedColorCode.initializeOnClickedColorCode(fag);
        fag.setWrapText(true); // Forces newline if the text use more width than the textbox is given.

        if (semesterCount < getCourses().size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
            GridPane.setConstraints(fagAndButton, semesterCount, count + 1);
        }
        else {
            GridPane.setConstraints(fagAndButton, semesterCount - getCourses().size() / 2, count + 6);
        }
        count++;

        fagAndButton.getChildren().addAll(fag, btn.getButton());

        //count = count % 4; // Used to make each semester consist of 4 courses.
        coursePlan.getChildren().add(fagAndButton);
    }

    public static void addCustomCourse(Course course, int semesterCount, int count) { // Adds a course to the courseplan.
        HBox fagAndButton = new HBox(0);

        Button button = new Button();

        TextArea fag = new TextArea(course.getCourseId() + "\n" + course.getCourseName() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);

        double size = 12;
        fag.setFont(Font.font(size));

        DragAndDrop.initializeDragAndDrop(fag);
        OnClickedColorCode.initializeOnClickedColorCode(fag);
        fag.setWrapText(true); // Forces newline if the text use more width than the textbox is given.

        if (semesterCount < getCourses().size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
            GridPane.setConstraints(fagAndButton, semesterCount, count);
        }
        else {
            GridPane.setConstraints(fagAndButton, semesterCount - getCourses().size() / 2, count + 5);
        }

        fagAndButton.getChildren().addAll(fag, button);

        coursePlan.getChildren().add(fagAndButton);
    }

    public static void colorCompleteCourses(int finishedSemesters) {
        int count = 0;
        for (double semester = 0; semester < finishedSemesters; semester++) {
            count++;
            for (int fag = 0; fag < courses.get((int) semester).size(); fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                getCourseTextArea(count).getStyleClass().remove("completed-courses");
                getCourseTextArea(count).getStyleClass().remove("all-courses");
                getCourseTextArea(count).getStyleClass().add("completed-courses");
                count++;
            }
        }

        for (double semester = finishedSemesters; semester < getCourses().size(); semester++) {
            count++;
            for (int fag = 0; fag < courses.get((int) semester).size(); fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                getCourseTextArea(count).getStyleClass().remove("all-courses");
                getCourseTextArea(count).getStyleClass().remove("completed-courses");
                getCourseTextArea(count).getStyleClass().add("all-courses");
                count++;
            }
        }
    }

    public static void colorCompleteSliderCourses(double sliderValue) { // Slider's functionality. Colors courses based on finished semesters.
        int count = 0;
        for (double semester = 0; semester < sliderValue * 2; semester++) {
            count++;
            for (int fag = 0; fag < courses.get((int) semester).size(); fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                getCourseTextArea(count).getStyleClass().remove("completed-courses");
                getCourseTextArea(count).getStyleClass().remove("all-courses");
                getCourseTextArea(count).getStyleClass().add("completed-courses");
                count++;
            }
        }

        for (double semester = sliderValue * 2; semester < getCourses().size(); semester++) {
            count++;
            for (int fag = 0; fag < courses.get((int) semester).size(); fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                getCourseTextArea(count).getStyleClass().remove("all-courses");
                getCourseTextArea(count).getStyleClass().remove("completed-courses");
                getCourseTextArea(count).getStyleClass().add("all-courses");
                count++;
            }
        }
    }

    public static ArrayList<Course> getCompletedCourses() {
        finishedCourses = new ArrayList<>();
        for (int i = 0; i < coursePlan.getChildren().size(); i++) {
            if (i % 5 == 0) {
                continue;
            }
            TextArea course = getCourseTextArea(count);
            String courseID = course.getText().split("\n")[0];


            if (course.getStyleClass().get(2).equals("completed-courses")) {

                for (ArrayList<Course> semester : courses) {
                    for (Course arrayCourse : semester) {
                        if (arrayCourse.getCourseId().toUpperCase().trim().equals(courseID.toUpperCase().trim())) {
                            finishedCourses.add(arrayCourse);
                        }
                    }
                }
            }
        }
        return finishedCourses;
    }

    public static void removeCourseFromCoursesArrayList(int index) {
        int counter = 0;
        for (int i = 0; i < courses.size(); i++) {
            counter++;

            for (int j = 0; j < courses.get(i).size(); j++) {
                if (counter == index) {
                    courses.get(i).remove(j);
                    return;
                }
                counter++;
            }
        }
    }
}
