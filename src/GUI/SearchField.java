package GUI;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SearchField {

    private TextField searchField;
    private ComboBox searchFieldOptions = new ComboBox();

    public TextField getSearchField() {
        return searchField;
    }

    public void setSearchField(TextField searchField) {
        searchField = searchField;
    }

    public ComboBox getSearchFieldOptions() {
        return searchFieldOptions;
    }

    public static void setSearchFieldOptions(ComboBox searchFieldOptions) {
        searchFieldOptions = searchFieldOptions;
    }

    public void initializeSearchField(HBox lowerSection) {
        searchField = new TextField();
        searchField.setPromptText("Search for courses.");
        searchField.setVisible(false);
    }


}
