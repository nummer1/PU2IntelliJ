package GUI;

import Algorithm.DbCom;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.Collection;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SearchField {

    private ComboBox searchField = new ComboBox();

    public ComboBox getSearchField() {
        return searchField;
    }

    public void initializeSearchField(HBox lowerSection) { // Initializes search-field.
        searchField.setPromptText("Search for courses.");
        searchField.setVisible(false);

        new AutoCompleteComboBoxListener<>(searchField);

        addExampleCourses();
        //searchField.getItems().addAll(new DbCom().getCourses());
    }

    public void addExampleCourses() {
        searchField.getItems().addAll("TDT4120 Algdat", "TDT4130 Blabla", "TDT4140 Fag");
    }
}
