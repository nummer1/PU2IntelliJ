package GUI;

import Algorithm.Course;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by andreaswilhelmflatt on 08.03.2017.
 */
public class DragAndDrop {

    public static void initializeDragAndDrop(TextArea fag) {
        fag.setOnDragDetected(event -> {
            String sourceText = fag.getText();

            Dragboard db = fag.startDragAndDrop(TransferMode.COPY_OR_MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(sourceText);

            db.setContent(content);
            event.consume();
        });

        fag.setOnDragOver(event -> {
            // If drag board has a string, let the event know that
            // the target accepts copy and move transfer modes
            Dragboard dragboard = event.getDragboard();

            if (dragboard.hasString())
            {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });

        fag.setOnDragDropped(event -> {
            // Transfer the data to the target
            Dragboard dragboard = event.getDragboard();

            if(event.getGestureSource() == event.getGestureTarget()) {return;}

            if (dragboard.hasString())
            {
                TextArea targetFag = new TextArea();
                TextArea sourceFag = new TextArea();

                int targetIndex = -1;
                int sourceIndex = -1;

                String sourceStyleClass = "";
                String targetStyleClass = "";

                for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {

                    if (MidSection.getLabelIndexes().contains(Integer.valueOf(i))) {continue;}

                    if (event.getGestureTarget().equals(MidSection.getCourseTextArea(i))) {
                        targetFag = MidSection.getCourseTextArea(i);
                        targetIndex = i;
                        targetStyleClass = targetFag.getStyleClass().get(2);
                    }
                    if (event.getGestureSource().equals(MidSection.getCourseTextArea(i))) {
                        sourceFag = MidSection.getCourseTextArea(i);
                        sourceIndex = i;
                        sourceStyleClass = sourceFag.getStyleClass().get(2);
                    }
                }

                targetFag.getStyleClass().set(2, sourceStyleClass);
                sourceFag.getStyleClass().set(2, targetStyleClass);

                String targetText = targetFag.getText();
                String sourceText = sourceFag.getText();

                MidSection.getCourseTextArea(targetIndex).setText(dragboard.getString()); // Set target text.
                MidSection.getCourseTextArea(sourceIndex).setText(targetText); // Set source text.

                // Data transfer is successful
                event.setDropCompleted(true);
            }
            else
            {
                // Data transfer is not successful
                event.setDropCompleted(false);
            }

            event.consume();
        });

    }
}