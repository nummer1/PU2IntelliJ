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

    public void setConfirmBtnAction(ChoiceBox fraChoices, TilFraChoices tilFraChoices) {
        confirmBtn.setOnAction(e -> {
            if (tilFraChoices.studiesIsSelected()) { // Checks if the user selected their preferred change.
                MidSection midSection = new MidSection();
                String fromStudy = fraChoices.getSelectionModel().getSelectedItem().toString();
                String toStudy = tilFraChoices.getTilChoices().getSelectionModel().getSelectedItem().toString();
                App.getLayout().setCenter(midSection.generateMidSection(fromStudy, toStudy));
                SemesterSliderAndInstructions.getSlider().setValue(0); // RESETS THE VALUE OF THE SLIDER TO 0
                SemesterSliderAndInstructions.getSliderAndText().setVisible(false); // SET SLIDER AND INSTRUCTION TEXT INVISIBLE
                App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
            }
            else {
                AlertBox.display("Feilmelding.", "Velg ønsket bytte av studie.");
            }
        });
    }
}