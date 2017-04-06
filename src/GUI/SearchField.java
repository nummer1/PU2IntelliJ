package GUI;

import Algorithm.DbCom;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SearchField {

    private static ComboBox searchField = new ComboBox();

    public static ComboBox getSearchField() {
        return searchField;
    }

    public static void initializeSearchField() { // Initializes search-field.
        searchField.setPromptText("Search for courses.");
        searchField.setVisible(false);

        new AutoCompleteComboBoxListener<>(searchField);

        addSearchAbleCourses();
    }

    private static void addSearchAbleCourses() {
        searchField.getItems().addAll(new DbCom().getCoursesAsString());
    }
}