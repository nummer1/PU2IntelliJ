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

public class ChatBox {

    private VBox chatBoxSection = new VBox(5);
    private final VBox chatBox = new VBox(5);
    private List<Label> messages = new ArrayList<>();
    private ScrollPane container = new ScrollPane();
    private int index = 0;
    private TopSection topSection;


    public ChatBox(TopSection topSection) {
        topSection = topSection;
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

        InputInterpreter anna = new InputInterpreter(topSection);
        TextField userInput = new TextField();

        userInput.setOnKeyPressed(keyPressed -> {
            if (keyPressed.getCode().equals(KeyCode.ENTER) && userInput.getLength() != 0) {

                messages.add(new Label(userInput.getText()));
                String answer = anna.interpret(userInput.getText());
                messages.add(new Message(user, answer));


                if (index % 2 == 0) {

                    messages.get(index).setAlignment(Pos.CENTER_LEFT);
                    System.out.println("1");

                } else {

                    messages.get(index).setAlignment(Pos.CENTER_RIGHT);
                    System.out.println("2");

                }

                Label messageLabel = new Label();
                messageLabel.setMinWidth(chatBoxSection.getWidth() - 2); // Subtract 2 in order to show the borders.
                messageLabel.setText(messages.get(index).getText());
                messageLabel.setStyle("-fx-border-color: gray;\n" +
                        "-fx-border-width: 1;\n" +
                        "-fx-background-color: white;\n");

                chatBox.getChildren().add(messageLabel);
                index++;

                userInput.clear();
            }
        });

        chatBoxSection.getChildren().addAll(annaLabel, container, userInput);
    }
}
 */