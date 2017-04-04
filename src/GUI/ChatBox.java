package GUI;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode; import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreaswilhelmflatt on 20/03/2017.
*/
public class ChatBox {

    private VBox chatBoxSection = new VBox(1);
    private final VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane();


    public ChatBox() {
        initChatBox();
        alwaysScrollToBottom();
    }

    private void alwaysScrollToBottom() {
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
        setWidth(600);
        container.setStyle("-fx-background: white");

        chatBoxSection.setAlignment(Pos.CENTER);

        /*Label annaLabel = new Label("Anna");

        annaLabel.setMinHeight(25);
        annaLabel.setMaxHeight(25);
        annaLabel.setMinWidth(200);
        annaLabel.setStyle("-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: white;\n");
        */

        InputInterpreter anna = new InputInterpreter();
        TextField userInput = new TextField();
        userInput.setPromptText("Ask ANNA.");


        userInput.setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode().equals(KeyCode.ENTER) && userInput.getLength() != 0) {

                Message userMessage = new Message(false, userInput.getText());
                Label inputMessage = new Label(userMessage.message);
                messages.add(new Label(inputMessage.getText()));
                chatBox.getChildren().add(styleLabel(userMessage));

                // no code for å disable userInput
                String speech = anna.interpret(userInput.getText());
                Message botMessage = new Message(true, speech);
                Label botMessageLabel = new Label(botMessage.message);
                messages.add(botMessageLabel);
                chatBox.getChildren().add(styleLabel(botMessage));

                userInput.clear();
            }
        });

        showFirstMessage();

        chatBoxSection.getChildren().addAll(container, userInput);
    }

    private void showFirstMessage() {
        Message firstMessage = new Message(true, "Hi, I'm Anna. Please let me know if there is anything I could help you with.");
        Label firstMessageLabel = new Label(firstMessage.message);
        messages.add(firstMessageLabel);
        chatBox.getChildren().add(styleLabel(firstMessage));
    }

    public HBox styleLabel(Message m) {

        HBox content = new HBox(10);

        Label label = new Label();
        if(messages.size() == 1) {
            label.setMaxWidth(370);
        }
        else {
            label.setMaxWidth(container.getWidth() * 0.95);
        }
        label.setWrapText(true);

        Region rightAlignTextField = new Region(); //
        content.setHgrow(rightAlignTextField, Priority.SOMETIMES); // Gives rightAlignTextField horizontal-space priority.

        if (m.isBot) {
            content.getChildren().addAll(label, rightAlignTextField);
            label.setStyle("-fx-border-width: 1; " +
                    "-fx-background-color: #55ACEE; " +
                    "-fx-font-size: 16pt; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-background-radius: 5px;" +
                    "-fx-text-fill: white;");
        }
        else {
            content.getChildren().addAll(rightAlignTextField, label);
            label.setStyle("-fx-border-width: 1; " +
                    "-fx-background-color: #D0D6D8; " +
                    "-fx-font-size: 16pt; " +
                    "-fx-border-radius: 5px; " +
                    "-fx-background-radius: 5px;");
        }

        //label.setMinWidth(chatBoxSection.getWidth() - 2); // Subtract 2 in order to show the borders.
        label.setText(m.message);

        return content;
    }

    private void setWidth(int width) {
        container.setPrefSize(width, 0.66 * width);
        chatBoxSection.setMaxWidth(width);
        chatBoxSection.setMinWidth(width);
        chatBox.setPrefWidth(width - 20);
        chatBox.setPrefWidth(width - 20);
    }
}
