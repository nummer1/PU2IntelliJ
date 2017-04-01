package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class TopSection {
    private VBox topSection = new VBox(10);
    private SearchField searchField;
    private ConfirmButton confirmBtn;
    private ChatBox chatBox;
    private TilFraChoices tilFraChoices;
    private SemesterSlider slider;

    public TopSection() {
        initializeTopSection();
    }

    public VBox getTopSection() {
        return topSection;
    }

    public SearchField getSearchField() {
        return searchField;
    }

    public ConfirmButton getConfirmBtn() {
        return confirmBtn;
    }

    public ChatBox getChatBox() {
        return chatBox;
    }

    public TilFraChoices getTilFraChoices() {
        return tilFraChoices;
    }

    public SemesterSlider getSlider() {
        return slider;
    }

    private void initializeTopSection1() {

    }

    private void initializeTopSection() {
        HBox upperSection = new HBox(10);
        HBox lowerSection = new HBox(10);
        upperSection.setAlignment(Pos.CENTER);

        tilFraChoices = new TilFraChoices();
        tilFraChoices.initializeTilFraListener(topSection);
        tilFraChoices.initializeConnectedComboBox();

        searchField = new SearchField();
        searchField.initializeSearchField(lowerSection);

        confirmBtn = new ConfirmButton();
        confirmBtn.setConfirmBtnAction(searchField, tilFraChoices.getFraChoices(), tilFraChoices);
        confirmBtn.getConfirmBtn().setDisable(true);

        //chatBox = new ChatBox();

        upperSection.getChildren().addAll(tilFraChoices.getFraLabel(), tilFraChoices.getFraChoices(), tilFraChoices.getTilLabel(), tilFraChoices.getTilChoices(), confirmBtn.getConfirmBtn()); //chatBox.getChatBox()

        slider = new SemesterSlider();
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
