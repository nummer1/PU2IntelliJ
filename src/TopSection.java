import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopSection {

    private static ObservableList<Study> studies = setAvailableStudies();
    private static ChoiceBox tilChoices = new ChoiceBox();
    private static ChoiceBox fraChoices = new ChoiceBox();
    private static Button confirmBtn = new Button("Bekreft");
    private static HBox topSection = new HBox(10);

    public static HBox generateTopSection() {
        topSection.setAlignment(Pos.CENTER);

        Label fraLabel = new Label("Fra: ");
        fraChoices.setMinWidth(200);

        Label tilLabel = new Label("Til: ");
        tilChoices.setMinWidth(200);

        ObservableList<String> studyNames = FXCollections.observableArrayList();

        studies.forEach(study -> studyNames.add(study.getStudy()));
        ConnectedComboBox<String> connectedComboBox = new ConnectedComboBox<>(studyNames);
        connectedComboBox.addComboBox(fraChoices);
        connectedComboBox.addComboBox(tilChoices);

        setConfirmBtnAction();

        topSection.getChildren().addAll(fraLabel, fraChoices, tilLabel, tilChoices, confirmBtn);

        return topSection;
    }

    public static void setConfirmBtnAction() {
        confirmBtn.setOnAction(e -> {
            if (studiesIsSelected()) {
                MidSection.getCoursePlan().getChildren().clear();
                MidSection.resetCounts();
                App.getLayout().setCenter(MidSection.generateMidSection(fraChoices.getSelectionModel().getSelectedItem().toString(), tilChoices.getSelectionModel().getSelectedItem().toString()));
                App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
            }
            else {
                AlertBox.display("Feilmelding.", "Velg ønsket bytte av studie.");
            }
        });
    }

    private static ObservableList<Study> setAvailableStudies() {
        ObservableList<Study> studies = FXCollections.observableArrayList();
        studies.add(new Study("Datateknologi"));
        studies.add(new Study("Kommunikasjonsteknologi"));
        studies.add(new Study("Informatikk"));
        return studies;
    }

    public static boolean studiesIsSelected() {
        if (fraChoices.getSelectionModel().isEmpty() || tilChoices.getSelectionModel().isEmpty()) {
            return false;
        }
        return true;
    }
}
