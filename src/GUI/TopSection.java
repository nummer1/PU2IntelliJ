package GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.awt.*;

public class TopSection {

    private Button changeInterfaceBtn;
    private SearchField searchField;
    private VBox topSection = new VBox(5);

    public TopSection() {
        initializeTopSection();
    }

    public VBox getTopSection() {
        return topSection;
    }

    public SearchField getSearchField(){
        return searchField;
    }

    private void initializeTopSection() {
        topSection.setAlignment(Pos.CENTER);
        HBox upperSection = new HBox(10);
        HBox lowerSection = new HBox(10);
        upperSection.setAlignment(Pos.CENTER);

        Region spacing = new Region();
        spacing.setMinHeight(0);
        spacing.setMaxHeight(0);

        changeInterfaceBtn = new Button("Change Interface.");
        changeInterfaceBtn.getStyleClass().add("change-interface-btn");

        changeInterfaceBtn.setOnAction(event -> {
            App2 app = new App2();
            app.start(App.getStage());
        });

        Instructions instructions = new Instructions();
        Instructions hidden = new Instructions();
        hidden.getInstructions().setVisible(false);

        TilFraChoices tilFraChoices = new TilFraChoices();
        tilFraChoices.initializeTilFraListener(topSection);
        tilFraChoices.initializeConnectedComboBox();

        searchField = new SearchField();
        searchField.getSearchField().getItems().clear();
        searchField.initializeSearchField();
        searchField.getSearchField().setMaxWidth(300);

        ConfirmButton confirmBtn = new ConfirmButton();
        confirmBtn.setConfirmBtnAction(searchField, tilFraChoices.getFraChoices(), tilFraChoices);

        Region alignTextField = new Region();
        Region alignTextField2 = new Region();
        upperSection.setHgrow(alignTextField, Priority.ALWAYS);
        upperSection.setHgrow(alignTextField2, Priority.ALWAYS);

        upperSection.getChildren().addAll(instructions.getInstructions(), alignTextField, tilFraChoices.getFraLabel(), tilFraChoices.getFraChoices(), tilFraChoices.getTilLabel(), tilFraChoices.getTilChoices(), confirmBtn.getConfirmBtn(), alignTextField2, hidden.getInstructions());

        SemesterSliderAndInstructions slider = new SemesterSliderAndInstructions();
        slider.initializeSliderListener();
        slider.getSliderAndText().setVisible(false);

        //GJØR SLIK AT SEARCHFIELD BLIR HØYRE-SENTRERT
        Region rightAlignTextField = new Region(); // Added because I want to right-align the search-field.
        lowerSection.setHgrow(rightAlignTextField, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.
        upperSection.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 20);
        lowerSection.setMaxWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 20);

        lowerSection.getChildren().addAll(slider.getSliderAndText(), rightAlignTextField, searchField.getInstructionsSearchFieldAndBtn());

        topSection.getChildren().addAll(spacing, changeInterfaceBtn, upperSection, lowerSection);
    }
}
