package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.awt.*;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class TilFraChoices {

    private ObservableList<Study> studies = setAvailableStudies();
    private ChoiceBox tilChoices = new ChoiceBox();
    private ChoiceBox fraChoices = new ChoiceBox();
    private Label fraLabel = new Label("From: ");
    private Label tilLabel = new Label("To: ");

    public ChoiceBox getTilChoices() {
        return tilChoices;
    }

    public ChoiceBox getFraChoices() {
        return fraChoices;
    }

    public Label getFraLabel() {
        return fraLabel;
    }

    public Label getTilLabel() {
        return tilLabel;
    }

    public TilFraChoices() {
        tilChoices.setMinWidth(200);
        fraChoices.setMinWidth(200);
    }

    public void initializeTilFraListener(VBox topSection) { // Sets top-section visible if a study is selected.
        fraChoices.valueProperty().addListener(e -> {
            topSection.getChildren().get(1).setVisible(true);

            MidSection midSection = new MidSection();
            midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
            midSection.resetCounts(); // Reset counts for indexing courses.
            SearchField.getInstructionsSearchFieldAndBtn().setVisible(true); // Set search-field visible.
            //checkCompletedCourses();

            App.getLayout().setCenter(midSection.showAllCoursesFrom(fraChoices.getSelectionModel().getSelectedItem().toString()));
            SemesterSliderAndInstructions.getSlider().setMax(MidSection.getCourses().size() / 2); // Divides by 10 because coursePlan (GridPane) consist of x(4 courses + 1 label) fields.
            SemesterSliderAndInstructions.getSliderAndText().setVisible(true);
            App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);

            if (fraChoices.getSelectionModel().isEmpty() || tilChoices.getSelectionModel().isEmpty()) {
                ConfirmButton.getConfirmBtn().setDisable(true);
            }
            else {
                ConfirmButton.getConfirmBtn().setDisable(false);
            }
        });

        tilChoices.valueProperty().addListener(e -> {
            topSection.getChildren().get(1).setVisible(true);
            if (fraChoices.getSelectionModel().isEmpty() || tilChoices.getSelectionModel().isEmpty()) {
                ConfirmButton.getConfirmBtn().setDisable(true);
            }
            else {
                ConfirmButton.getConfirmBtn().setDisable(false);
            }
        });
    }

    public void initializeConnectedComboBox() {
        ObservableList<String> studyNames = FXCollections.observableArrayList();

        studies.forEach(study -> studyNames.add(study.getStudy()));
        ConnectedComboBox<String> connectedComboBox = new ConnectedComboBox<>(studyNames);
        connectedComboBox.addComboBox(fraChoices);
        connectedComboBox.addComboBox(tilChoices);
    }

    private ObservableList<Study> setAvailableStudies() {
        ObservableList<Study> studies = FXCollections.observableArrayList();
        studies.add(new Study("Datateknologi"));
        studies.add(new Study("Kommunikasjonsteknologi"));
        studies.add(new Study("Informatikk"));
        return studies;
    }

    public boolean studiesIsSelected() { // Checks if both studies is selected.
        if (fraChoices.getSelectionModel().isEmpty() || tilChoices.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }
}
