package GUI;

import Algorithm.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andreaswilhelmflatt on 20.02.2017.
 */
public class MidSection {

    private static GridPane coursePlan = new GridPane();
    private static int semesterCount = -1;
    private static int count = 0;
    private static ArrayList<ArrayList<Course>> courses;
    private static ArrayList<Course> finishedCourses = new ArrayList<>();

    public static GridPane getCoursePlan () {
        return coursePlan;
    } // Returns courseplan.

    public static void resetCounts() { // Resets counts.
        semesterCount = -1;
        count = 0;
    }

    public static ArrayList<ArrayList<Course>> getCourses() {
        return courses;
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

        generateCoursePlan(courses);

        return coursePlan;
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
        for (ArrayList<Course> semester : courses) {
            count = 0;
            for (Course course : semester) {
                addCourse(course);
            }
        }
    }

    private void makeBasicGridPane() { // Initializes GridPane.
        coursePlan.getStylesheets().add(MidSection.class.getResource("stylesheets.css").toExternalForm());
        coursePlan.setPadding(new Insets(10, 10, 10, 10));
        coursePlan.setVgap(3);
        coursePlan.setHgap(3);
    }

    private void addCourse(Course course) { // Adds a course to the courseplan.
        if (count == 0) { // Checks if it needs to start a new semester.
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
        }

        TextArea fag = new TextArea(course.getCourseId() + "\n" + course.getCourseName() + "\n" + "Eksamensdato: " + course.getPrintable_date());
        fag.getStyleClass().add("all-courses");
        fag.setEditable(false);

        double size = 12;
        fag.setFont(Font.font(size));

        DragAndDrop.initializeDragAndDrop(fag);
        OnClickedColorCode.initializeOnClickedColorCode(fag);
        fag.setWrapText(true); // Forces newline if the text use more width than the textbox is given.

        if (semesterCount < getCourses().size() / 2) { // Checks if GUI needs to start on the lower section of the study-plan (semester 6-10).
            GridPane.setConstraints(fag, semesterCount, count + 1);
        }
        else {
            GridPane.setConstraints(fag, semesterCount - getCourses().size() / 2, count + 6);
        }
        count++;
        //count = count % 4; // Used to make each semester consist of 4 courses.
        coursePlan.getChildren().add(fag);
    }

    public static void colorCompleteCourses(int finishedSemesters) {
        int count = 0;
        for (double semester = 0; semester < finishedSemesters; semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("completed-courses");
                count++;
            }
        }

        for (double semester = finishedSemesters; semester < getCourses().size(); semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("all-courses");
                count++;
            }
        }
    }

    public static void colorCompleteSliderCourses(double sliderValue) { // Slider's functionality. Colors courses based on finished semesters.
        int count = 0;
        for (double semester = 0; semester < sliderValue * 2; semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("completed-courses");
                count++;
            }
        }

        for (double semester = sliderValue * 2; semester < getCourses().size(); semester++) {
            count++;
            for (int fag = 0; fag < 4; fag++) {
                if (count == coursePlan.getChildren().size()) {
                    return;
                }
                coursePlan.getChildren().get(count).getStyleClass().remove("all-courses");
                coursePlan.getChildren().get(count).getStyleClass().remove("completed-courses");
                coursePlan.getChildren().get(count).getStyleClass().add("all-courses");
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
            TextArea course = (TextArea) coursePlan.getChildren().get(i);
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
}
