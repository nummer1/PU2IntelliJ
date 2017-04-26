package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode; import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreaswilhelmflatt on 20/03/2017.
*/
public class ChatBox {

    private VBox chatBoxSection = new VBox(1);
    private final VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane(); // CONTAINS CHATBOX
    private static InputInterpreter anna;


    public ChatBox() {
        initChatBox();
        alwaysScrollToBottom();
    }

    private void alwaysScrollToBottom() { // MAKES THE MESSAGE-FIELD ALWAYS SCROLLED TO THE BOTTOM
        chatBox.heightProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldvalue, Object newValue) {
                container.setVvalue((Double)newValue );
            }
        });
    }

    public VBox getChatBox() {
        return chatBoxSection;
    }

    private void initChatBox() {
        container.setContent(chatBox);
        setDimensions(800);
        container.setStyle("-fx-background: white");

        chatBoxSection.setAlignment(Pos.CENTER);

        anna = new InputInterpreter(); // ENABLES THE AI TO INTERPRET USER INPUT
        TextField userInput = new TextField();
        userInput.setPromptText("Ask ANNA.");


        userInput.setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode().equals(KeyCode.ENTER) && userInput.getLength() != 0) { // ALIGNS MESSAGE PROPERLY, DEPENDING ON THE SENDER

                Message userMessage = new Message(false, userInput.getText());
                Label inputMessage = new Label(userMessage.message);
                messages.add(new Label(inputMessage.getText()));
                chatBox.getChildren().add(styleLabel(userMessage));

                // no code for å disable userInput
                String speech = anna.interpret(userInput.getText()); // INTERPRET USER INPUT
                Message botMessage = new Message(true, speech);
                Label botMessageLabel = new Label(botMessage.message);
                messages.add(botMessageLabel);
                chatBox.getChildren().add(styleLabel(botMessage));

                userInput.clear();
            }
        });

        showFirstMessage(); //

        chatBoxSection.getChildren().addAll(container, userInput);
    }

    private void showFirstMessage() { // SHOWS THE INTRODUCING MESSAGE FROM ANNA
        Message firstMessage = new Message(true, "Hi, I'm Anna. Please let me know if there is anything I could help you with.");
        Label firstMessageLabel = new Label(firstMessage.message);
        messages.add(firstMessageLabel);
        chatBox.getChildren().add(styleLabel(firstMessage));
    }

    private HBox styleLabel(Message m) { // MAKES A LABEL FX-ELEMENT, COLORED WITH THE RIGHT COLOR (DEPENDING ON THE SENDER) ETC.

        HBox content = new HBox(10);

        Label label = new Label();
        if(messages.size() == 1) {
            label.setMaxWidth(370);
        }
        else {
            label.setMaxWidth(container.getWidth() * 0.95);
        }
        label.setWrapText(true);

        Region rightAlignTextField = new Region(); // REGION FOR ALIGNING LABEL CORRECTLY IF IT NEEDS TO BE ALIGNED TO THE RIGHT. REGION FILLS THE LEFT-SECTION
        content.setHgrow(rightAlignTextField, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.

        if (m.isBot) {
            content.getChildren().addAll(label, rightAlignTextField);
            label.getStyleClass().add("aichat");
        }
        else {
            content.getChildren().addAll(rightAlignTextField, label);
            label.getStyleClass().add("userchat");
        }

        //label.setMinWidth(chatBoxSection.getWidth() - 2); // Subtract 2 in order to show the borders.
        label.setText(m.message);

        return content;
    }

    private void setDimensions(int dimension) { // SET DIMENSIONS OF THE CHATBOT
        container.setPrefSize(dimension, 0.66 * dimension);
        chatBoxSection.setMaxWidth(dimension);
        chatBoxSection.setMinWidth(dimension);
        chatBox.setPrefWidth(dimension - 20);
        chatBox.setPrefWidth(dimension - 20);
    }

    public InputInterpreter getInputInterpreter() {
        return anna;
    }
}
