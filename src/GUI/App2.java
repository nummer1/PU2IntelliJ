package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App2 extends Application {

    private Stage window;
    private static VBox layout = new VBox(10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ANNABot");

        Button changeInterfaceBtn = new Button("Change Interface.");

        changeInterfaceBtn.setOnAction(event -> {
            App app = new App();
            app.start(window);
        });

        ChatBox chatBox = new ChatBox();

        layout.setStyle("-fx-background-color: #fff9c4;");
        layout.getChildren().addAll(changeInterfaceBtn, chatBox.getChatBox());

        window.setScene(new Scene(layout, 800, 400));
        window.show();
    }

    public static VBox getLayout() {
        return layout;
    }
}
