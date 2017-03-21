package GUI;

import javafx.scene.control.TextArea;

/**
 * Created by andreaswilhelmflatt on 13.03.2017.
 */
public class OnClickedColorCode {

    public static void initializeOnClickedColorCode(TextArea fag) {
        fag.setOnMouseClicked(event -> {

            for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {
                if (event.getSource().equals(MidSection.getCoursePlan().getChildren().get(i))) {
                    if (MidSection.getCoursePlan().getChildren().get(i).getStyleClass().get(2).equals("all-courses")) {
                        MidSection.getCoursePlan().getChildren().get(i).getStyleClass().remove("all-courses");
                        MidSection.getCoursePlan().getChildren().get(i).getStyleClass().add("completed-courses");
                    } else {
                        MidSection.getCoursePlan().getChildren().get(i).getStyleClass().remove("completed-courses");
                        MidSection.getCoursePlan().getChildren().get(i).getStyleClass().add("all-courses");
                    }
                }
            }
        });
    }
}
