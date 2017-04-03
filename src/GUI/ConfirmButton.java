package GUI;

import Algorithm.Semester;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import static GUI.MidSection.getCompletedCourses;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class ConfirmButton {

    private static final Button confirmBtn = new Button("Bekreft");

    public static Button getConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtnAction(SearchField searchField, ChoiceBox fraChoices, TilFraChoices tilFraChoices) {
        // Checks if the user selected their preferred change.
        confirmBtn.setOnAction(e -> {
            if (tilFraChoices.studiesIsSelected()) {
                getCompletedCourses();
                MidSection midSection = new MidSection();
                midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
                midSection.resetCounts(); // Reset counts for indexing courses.
                searchField.getSearchField().setVisible(true); // Set search-field visible.
                //checkCompletedCourses();

                App.getLayout().setCenter(midSection.generateMidSection(fraChoices.getSelectionModel().getSelectedItem().toString(), tilFraChoices.getTilChoices().getSelectionModel().getSelectedItem().toString(), 2));
                SemesterSlider.getSlider().setMax(Math.ceil(midSection.getCoursePlan().getChildren().size()/10.0)); // Divides by 10 because coursePlan (GridPane) consist of x(4 courses + 1 label) fields.
                SemesterSlider.getSlider().setVisible(true);
                App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
                System.out.println(Math.ceil(midSection.getCoursePlan().getChildren().size()/10.0));
            }
            else {
                AlertBox.display("Feilmelding.", "Velg ønsket bytte av studie.");
            }
        });
    }
}
