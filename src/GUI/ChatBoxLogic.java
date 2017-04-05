package GUI;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Created by andreaswilhelmflatt on 05/04/2017.
 */
public class ChatBoxLogic {

    private static MidSection midSection;
    private static HBox searchField;
    private static GridPane coursePlan;

    private static void showAllCoursesFrom(String from) {
        midSection = new MidSection();
        midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
        midSection.resetCounts(); // Reset counts for indexing courses.

        coursePlan = midSection.showAllCoursesFrom(from);
        coursePlan.setMaxHeight(450);
    }

    private static void searchField() { // Call this method _after_ calling showAllCoursesFrom.
        searchField = new HBox(10);

        Region region = new Region();
        searchField.setHgrow(region, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.

        SearchField searchField = new SearchField();
        searchField.getSearchField().setMaxWidth(300);
        searchField.initializeSearchField();
        searchField.getSearchField().setVisible(true);

        ChatBoxLogic.searchField.getChildren().addAll(region, searchField.getSearchField());
    }

    public static void showUserCoursesFrom(String from, int fininishedSemesters) { // Call this method when you want to show the user all courses from the Study he/she's been taking.
        showAllCoursesFrom(from);
        searchField();
        MidSection.colorCompleteCourses(fininishedSemesters);

        App2.getLayout().getChildren().addAll(searchField, coursePlan);
    }
}
