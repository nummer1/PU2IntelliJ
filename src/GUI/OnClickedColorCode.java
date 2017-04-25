package GUI;

import javafx.scene.control.TextArea;

/**
 * Created by andreaswilhelmflatt on 13.03.2017.
 */
public class OnClickedColorCode {

    public static void initializeOnClickedColorCode(TextArea fag) {
        fag.setOnMouseClicked(event -> {


            for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {
                if (MidSection.getLabelIndexes().contains(Integer.valueOf(i))) {continue;}

                if (event.getSource().equals(MidSection.getCourseTextArea(i))) {
                    if (MidSection.getCourseTextArea(i).getStyleClass().get(2).equals("all-courses")) {
                        MidSection.getCourseTextArea(i).getStyleClass().remove("all-courses");
                        MidSection.getCourseTextArea(i).getStyleClass().add("completed-courses");
                    } else {
                        MidSection.getCourseTextArea(i).getStyleClass().remove("completed-courses");
                        MidSection.getCourseTextArea(i).getStyleClass().add("all-courses");
                    }
                }
            }
        });
    }
}
