package GUI;

import javafx.scene.control.TextArea;

/**
 * Created by andreaswilhelmflatt on 19/04/2017.
 */
public class Instructions {

    private TextArea instructions;

    public Instructions() {
        instructions = new TextArea();
        instructions.setText("blldldldl");
        instructions.setPrefSize(400, 150);
    }

    public TextArea getInstructions() {return instructions;}
}
