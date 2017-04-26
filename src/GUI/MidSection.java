package GUI;

import Algorithm.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
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

    private static GridPane coursePlan = new GridPane(); // THE COURSEPLAN SHOWN IN THE GUI
    private static int semesterCount = -1; // INDEX FOR KEEPING TRACK OF WHAT SEMESTER CURRENTLY ON WHEN ADDING COURSES
    private static int count = 0; // INDEX FOR KEEPING TRACK OF WHAT COURSE CURRENTLY ON WHEN ADDING COURSES
    private static ArrayList<ArrayList<Course>> courses; // ALL COURSES STORED AS COURSE-OBJECTS
    private static ArrayList<Course> finishedCourses = new ArrayList<>(); // ALL FINISHEDCOURSES (ALL COURSES COLOR-CODED GREEN)
    private static ArrayList<Integer> labelIndexes = new ArrayList<>(); // INDEXES FOR LABELS IN THE COURSEPLAN

    public static GridPane getCoursePlan () {return coursePlan;} // Returns courseplan.

    public static ArrayList<Integer> getLabelIndexes(){return labelIndexes;}

    public static ArrayList<ArrayList<Course>> getCourses() {return courses;}

    public static void resetCounts() { // Resets counts.
        semesterCount = -1;
        count = 0;
    }

    private static Collection<String> getStudyPlanCoursesAsString() { // RETURNS ALL COURSES IN AN ARRAYLIST<STRING> WITH THE COURSE'S NAME
        Collection<String> coursesAsString = new ArrayList<>();

        for (ArrayList<Course> semester : courses) {
            for (Course course : semester) {
                coursesAsString.add(course.getCourseId() + ":" + course.getCourseName());
            }
        }
        return coursesAsString;
    }

    public GridPane showAllCoursesFrom(String from) { // SHOWS ALL COURSES FROM A SPECIFIED STUDY
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

    public static TextArea getCourseTextArea(int index) { // GETS COURSEPLAN TEXTAREA # INDEX
        return ((TextArea)((HBox) coursePlan.getChildren().get(index)).getChildren().get(0));
    }

    public static void updateLabelIndexes() { // CHECKS IF LABELINDEXES HAS CHANGED (DUE TO FOR EXAMPLE A REMOVED COURSE OR SIMILAR)
        labelIndexes.clear();
        int counter = 0;

        for (ArrayList<Course> semester : courses) {
            labelIndexes.add(counter);
            counter++; // ADDS 1 FOR THE LABEL
            counter += semester.size(); // ADDS MORE DEPENDING ON HOW MANY COURSES IN THE SEMESTER
        }
    }

    public static Button getCourseButton(int index) { // RETURNS THE BUTTON AT INDEX I IN COURSEPLAN
        return ((Button) ((HBox) coursePlan.getChildren().get(index)).getChildren().get(1));
    }

    public GridPane generateMidSection(String from, String to) { // GENERATES A COURSEPLAN THAT CHECKS WHICH COURSES IS ALREADY TAKEN, AND REMOVES THEM FROM THE SUGGESTED COURSEPLAN
        makeBasicGridPane();

        Selector sel = new Selector();

        DbCom dbcom = new DbCom();

        String toStudyCode = dbcom.getStudyCode(to);
        ArrayList<Course> finishedCourses = new ArrayList<>();
        finishedCourses.addAll(getCompletedCourses());
        StudyPlan studyplan = sel.switchMajor(finishedCourses, toStudyCode, "autumn"); // RETURNS THE SUGGESTED COURSEPLAN
        Collection<Semester> semesters = studyplan.getSemesters();

        courses.clear(); // CLEARS COURSES IF PREVIOUS PLAN EXISTS

        for (Semester semester : semesters) {
            ArrayList<Course> sem = new ArrayList<>(semester.getCourses());
            courses.add(sem);
        }

        getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
        resetCounts(); // Reset counts for indexing courses.

        for (ArrayList<Course> semester : courses) {
            semester.removeAll(finishedCourses);
        }

        generateCoursePlan(courses);
        return coursePlan;
    }

    public static void generateCoursePlan(ArrayList<ArrayList<Course>> courses) {
        coursePlan.getChildren().clear();
        labelIndexes.clear();
        resetCounts();

        int labelCount = 0;

        for (ArrayList<Course> semester : courses) {
            labelIndexes.add(labelCount); // ADDS THE INDEX OF THE LABEL TO LABELCOUNTS TO KEEP TRACK OF THESE FOR USE LATER
            labelCount++;

            count = 0;

            semesterCount++;
            Label semesterLabel = new Label("Semester " + Integer.toString(semesterCount + 1));

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

        coursePlan.getChildren().add(fagAndButton);
    }

    public static void colorCompleteCourses(int finishedSemesters) { // COLORS ALL COURSES GREEN IN SEMESTER I <= FINISHEDSEMESTERS
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

    public static ArrayList<Course> getCompletedCourses() { // Returns all completed (green) courses.
        finishedCourses = new ArrayList<>();
        for (int i = 0; i < coursePlan.getChildren().size(); i++) {
            if (MidSection.getLabelIndexes().contains(Integer.valueOf(i))) {
                continue;
            }
            TextArea course = getCourseTextArea(i);
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

    public static void removeCourseFromCoursesArrayList(int index) { // Removes a course from courses-arrayList, with index as input (index comes from the courseplan)
        int counter = 0;
        for (int i = 0; i < courses.size(); i++) {
            counter++;

            for (int j = 0; j < courses.get(i).size(); j++) {
                if (counter == index) {
                    SearchField.getSearchField().getItems().add(courses.get(i).get(j).getCourseId() + ":" + courses.get(i).get(j).getCourseName());
                    courses.get(i).remove(j);
                    return;
                }
                counter++;
            }
        }
    }
}
