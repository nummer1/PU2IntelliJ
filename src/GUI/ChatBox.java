package GUI;

import javafx.geometry.Pos;
        import javafx.scene.control.*;
        import javafx.scene.input.KeyCode;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by andreaswilhelmflatt on 20/03/2017.
*/
public class ChatBox {

    private VBox chatBoxSection = new VBox(5);
    private final VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane();
    private TopSection topSection;


    public ChatBox(TopSection topSection) {
        this.topSection = topSection;
        initChatBox();
    }

    public VBox getChatBox() {
        return chatBoxSection;
    }

    private void initChatBox() {
        container.setPrefSize(200, 200);
        container.setContent(chatBox);

        Label annaLabel = new Label("Anna");

        annaLabel.setMinHeight(25);
        annaLabel.setMaxHeight(25);
        annaLabel.setMinWidth(200);
        annaLabel.setStyle("-fx-border-color: black;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: white;\n");

        InputInterpreter anna = new InputInterpreter(this.topSection);
        TextField userInput = new TextField();

        userInput.setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode().equals(KeyCode.ENTER) && userInput.getLength() != 0) {

                Message userMessage = new Message(false, userInput.getText());
                messages.add(new Label(userMessage.message));
                chatBox.getChildren().add(styleLabel(userMessage));
                // no code for å disable userInput
                String speech = anna.interpret(userInput.getText());
                Message botMessage = new Message(true, speech);
                messages.add(new Label(botMessage.message));
                chatBox.getChildren().add(styleLabel(botMessage));

                userInput.clear();
            }
        });

        chatBoxSection.getChildren().addAll(annaLabel, container, userInput);
    }

    public Label styleLabel(Message m) {

        Label label = new Label();

        if (m.isBot) {
            label.setAlignment(Pos.CENTER_LEFT);
        }
        else {
            label.setAlignment(Pos.CENTER_RIGHT);
        }

        label.setMinWidth(chatBoxSection.getWidth() - 2); // Subtract 2 in order to show the borders.
        label.setText(m.message);
        label.setStyle("-fx-border-color: gray;\n" +
                "-fx-border-width: 1;\n" +
                "-fx-background-color: white;\n");
        return label;
    }
}
