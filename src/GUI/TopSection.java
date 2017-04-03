package GUI;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TopSection {

    public SearchField searchField;

    private VBox topSection = new VBox(10);

    public TopSection() {
        initializeTopSection();
    }

    public VBox getTopSection() {
        return topSection;
    }

    private void initializeTopSection() {
        HBox upperSection = new HBox(10);
        HBox lowerSection = new HBox(10);
        upperSection.setAlignment(Pos.CENTER);
        /*
        ChatBox chatBox = new ChatBox(this);
        upperSection.getChildren().add(chatBox.getChatBox());
        */


        TilFraChoices tilFraChoices = new TilFraChoices();
        tilFraChoices.initializeTilFraListener(topSection);
        tilFraChoices.initializeConnectedComboBox();

        SearchField searchField = new SearchField();
        searchField.initializeSearchField(lowerSection);

        ConfirmButton confirmBtn = new ConfirmButton();
        confirmBtn.setConfirmBtnAction(searchField, tilFraChoices.getFraChoices(), tilFraChoices);

        upperSection.getChildren().addAll(tilFraChoices.getFraLabel(), tilFraChoices.getFraChoices(), tilFraChoices.getTilLabel(), tilFraChoices.getTilChoices(), confirmBtn.getConfirmBtn()/*, chatBox.getChatBox()*/);

        SemesterSlider slider = new SemesterSlider();
        slider.initializeSliderListener();
        slider.getSlider().setVisible(false);

        //GJØR SLIK AT SEARCHFIELD BLIR HØYRE-SENTRERT
        Region rightAlignTextField = new Region(); // Added because I want to right-align the search-field.
        lowerSection.setHgrow(rightAlignTextField, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.
        lowerSection.setVisible(false);

        lowerSection.getChildren().addAll(slider.getSlider(), rightAlignTextField, searchField.getSearchField());

        topSection.getChildren().addAll(upperSection, lowerSection);
    }
}
