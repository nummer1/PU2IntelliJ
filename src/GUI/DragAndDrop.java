package GUI;

import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

/**
 * Created by andreaswilhelmflatt on 08.03.2017.
 */
public class DragAndDrop {

    public static void initializeDragAndDrop(TextArea fag) {
        fag.setOnDragDetected(event -> {
            System.out.println("onDragDetected");

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

                for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {
                    if (event.getGestureTarget().equals(MidSection.getCoursePlan().getChildren().get(i))) {
                        targetFag = (TextArea) MidSection.getCoursePlan().getChildren().get(i);
                        targetIndex = i;
                    }
                    if (event.getGestureSource().equals(MidSection.getCoursePlan().getChildren().get(i))) {
                        sourceFag = (TextArea) MidSection.getCoursePlan().getChildren().get(i);
                        sourceIndex = i;
                    }
                }

                String targetText = targetFag.getText();
                String sourceText = sourceFag.getText();

                ((TextArea) MidSection.getCoursePlan().getChildren().get(targetIndex)).setText(dragboard.getString()); // Set target text.
                ((TextArea) MidSection.getCoursePlan().getChildren().get(sourceIndex)).setText(targetText); // Set source text.
                System.out.println(targetFag.getText());

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

    public static void initializeDragAndDropComboBox(ComboBox fag) {
        fag.setOnDragDetected(event -> {
            System.out.println("onDragDetected");

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

                for (int i = 0; i < MidSection.getCoursePlan().getChildren().size(); i++) {
                    if (event.getGestureTarget().equals(MidSection.getCoursePlan().getChildren().get(i))) {
                        targetFag = (TextArea) MidSection.getCoursePlan().getChildren().get(i);
                        targetIndex = i;
                    }
                    if (event.getGestureSource().equals(MidSection.getCoursePlan().getChildren().get(i))) {
                        sourceFag = (TextArea) MidSection.getCoursePlan().getChildren().get(i);
                        sourceIndex = i;
                    }
                }

                String targetText = targetFag.getText();
                String sourceText = sourceFag.getText();

                ((TextArea) MidSection.getCoursePlan().getChildren().get(targetIndex)).setText(dragboard.getString()); // Set target text.
                ((TextArea) MidSection.getCoursePlan().getChildren().get(sourceIndex)).setText(targetText); // Set source text.
                System.out.println(targetFag.getText());

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