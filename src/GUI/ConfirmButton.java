package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class ConfirmButton {

    private final Button confirmBtn = new Button("Bekreft");

    public Button getConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtnAction(SearchField searchField, ChoiceBox fraChoices, TilFraChoices tilFraChoices) {
        // Checks if the user selected their preferred change.
        confirmBtn.setOnAction(e -> {
            if (tilFraChoices.studiesIsSelected()) {
                MidSection midSection = new MidSection();
                midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
                midSection.resetCounts(); // Reset counts for indexing courses.
                searchField.getSearchField().setVisible(true); // Set search-field visible.
                //checkCompletedCourses();

                App.getLayout().setCenter(midSection.generateMidSection(fraChoices.getSelectionModel().getSelectedItem().toString(), tilFraChoices.getTilChoices().getSelectionModel().getSelectedItem().toString()));
                App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
            }
            else {
                AlertBox.display("Feilmelding.", "Velg ønsket bytte av studie.");
            }
        });
    }
}
