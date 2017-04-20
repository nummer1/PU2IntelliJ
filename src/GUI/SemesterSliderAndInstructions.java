package GUI;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.text.ParseException;

import static GUI.MidSection.colorCompleteSliderCourses;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SemesterSliderAndInstructions {

    private static Slider slider = new Slider();
    private static VBox sliderAndText = new VBox(5);

    public SemesterSliderAndInstructions() {
        slider.setMin(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(1);
        //slider.setBlockIncrement(0.5);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(1);
        slider.setSnapToTicks(true);
        slider.setMinWidth(400);
    }

    public static Slider getSlider() {
        return slider;
    }

    public static VBox getSliderAndText() {
        return sliderAndText;
    }

    public void initializeSliderListener() { // Listens if slider-value changes.
        sliderAndText.getChildren().clear();
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorCompleteSliderCourses(newValue.doubleValue());
        });

        Label sliderText = new Label("Finished Years");
        sliderText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        sliderAndText.getChildren().addAll(sliderText, slider);
    }
}
