package GUI;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by andreaswilhelmflatt on 19/04/2017.
 */
public class Instructions {

    private VBox instructions;

    public Instructions() { // MAKES THE INSTRUCTIONBOX SHOWN IN APP (THE ONE WITHOUT THE CHATBOT)
        instructions = new VBox(5);

        Label instructionsLabel = new Label("Instructions");
        instructionsLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 16)); // FONTSIZE 16

        TextArea instructionsArea = new TextArea();
        instructionsArea.setEditable(false);
        instructionsArea.setFont(Font.font(14)); // FONTSIZE 14
        instructionsArea.getStyleClass().add("instructions");
        instructionsArea.setWrapText(true);
        instructionsArea.setText("1: Choose desired study to switch from, and to. \n" +
                "2: Select completed years at the study you're changing from. \n" +
                "3: Click extra courses that you already finished. \n" +
                "4: Add courses with the searchfield to customize your studyplan. \n" +
                "5: Submit.");
        instructionsArea.setPrefSize(400, 150);

        instructions.getChildren().addAll(instructionsLabel, instructionsArea);
    }

    public VBox getInstructions() {return instructions;}
}
