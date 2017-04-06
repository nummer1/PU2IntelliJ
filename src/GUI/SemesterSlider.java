package GUI;

import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.text.ParseException;

import static GUI.MidSection.colorCompleteSliderCourses;

/**
 * Created by andreaswilhelmflatt on 05.03.2017.
 */
public class SemesterSlider {

    private static Slider slider = new Slider();

    public SemesterSlider() {
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

    public void initializeSliderListener() { // Listens if slider-value changes.
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            colorCompleteSliderCourses(newValue.doubleValue());
        });
    }
}
