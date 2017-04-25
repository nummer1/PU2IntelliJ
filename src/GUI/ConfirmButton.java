package GUI;

import Algorithm.Course;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.util.ArrayList;

import static GUI.MidSection.getCompletedCourses;

public class ConfirmButton {

    private static final Button confirmBtn = new Button("Submit");

    public static Button getConfirmBtn() {
        return confirmBtn;
    }

    public void setConfirmBtnAction(SearchField searchField, ChoiceBox fraChoices, TilFraChoices tilFraChoices) {
        // Checks if the user selected their preferred change.
        confirmBtn.setOnAction(e -> {
            if (tilFraChoices.studiesIsSelected()) {
                MidSection midSection = new MidSection();
                App.getLayout().getChildren().remove(midSection.generateMidSection(fraChoices.getSelectionModel().getSelectedItem().toString(), tilFraChoices.getTilChoices().getSelectionModel().getSelectedItem().toString()));
                App.getLayout().setCenter(midSection.generateMidSection(fraChoices.getSelectionModel().getSelectedItem().toString(), tilFraChoices.getTilChoices().getSelectionModel().getSelectedItem().toString()));
                SemesterSliderAndInstructions.getSlider().setValue(0);
                SemesterSliderAndInstructions.getSliderAndText().setVisible(false);
                App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
            }
            else {
                AlertBox.display("Feilmelding.", "Velg ønsket bytte av studie.");
            }
        });
    }
}