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
    private static HBox searchFieldHBox;
    private static GridPane coursePlan;

    private static void showAllCoursesFrom(String from) {
        midSection = new MidSection();
        midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
        midSection.resetCounts(); // Reset counts for indexing courses.

        coursePlan = midSection.showAllCoursesFrom(from);
        coursePlan.setMaxHeight(720);
    }

    private static void searchField() { // Call this method _after_ calling showAllCoursesFrom.
        searchFieldHBox = new HBox(10);

        Region region = new Region();
        searchFieldHBox.setHgrow(region, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.

        SearchField.getSearchField().setMaxWidth(300);
        SearchField.getSearchField().getItems().clear();
        SearchField.initializeSearchField();
        SearchField.getInstructionsSearchFieldAndBtn().setVisible(true);

        ChatBoxLogic.searchFieldHBox.getChildren().addAll(region, SearchField.getInstructionsSearchFieldAndBtn());
    }

    public static void showUserCoursesFrom(String from, int fininishedSemesters) { // Call this method when you want to show the user all courses from the Study he/she's been taking.
        showAllCoursesFrom(from);
        searchField();
        MidSection.colorCompleteCourses(fininishedSemesters);

        App2.getLayout().getChildren().addAll(searchFieldHBox, coursePlan);
    }
}
