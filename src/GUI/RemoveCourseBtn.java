package GUI;

import Algorithm.Course;
import Algorithm.DbCom;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * Created by andreaswilhelmflatt on 18/04/2017.
 */
public class RemoveCourseBtn {

    private Button removeCourseBtn = new Button();

    public RemoveCourseBtn(int index) {
        initializeRemoveCourseBtn();
    }

    public Button getButton() {return removeCourseBtn;}

    public void initializeRemoveCourseBtn() {
        removeCourseBtn.setText("x");
        removeCourseBtn.getStyleClass().add("remove-course-btn");
        removeCourseBtn.setMaxWidth(50);

        removeCourseBtn.setOnAction(event -> {
            for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {
                if (MidSection.getLabelIndexes().contains(Integer.valueOf(i))) {continue;}

                if (event.getTarget().equals(MidSection.getCourseButton(i))) {
                    MidSection.removeCourseFromCoursesArrayList(i);
                    MidSection.getCoursePlan().getChildren().remove(i);
                    MidSection.updateLabelIndexes();
                }
            }
        });
    }
}
