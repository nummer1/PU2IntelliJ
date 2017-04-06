package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App2 extends Application {

    private static Stage window;
    private static VBox layout = new VBox(10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ANNABot");

        Button changeInterfaceBtn = new Button("Change Interface.");
        changeInterfaceBtn.getStyleClass().add("change-interface-btn");

        changeInterfaceBtn.setOnAction(event -> {
            App app = new App();
            app.start(window);
        });

        ChatBox chatBox = new ChatBox();
        layout.setAlignment(Pos.CENTER);

        layout.setStyle("-fx-background-color: #fff9c4;");
        layout.getChildren().addAll(changeInterfaceBtn, chatBox.getChatBox());

        if (App.getStage() != null) {
            App.getStage().getScene().setRoot(new Region());
            App.getLayout().getChildren().clear();
        }

        window.setScene(new Scene(layout, 800, 400));
        layout.getStylesheets().add(getClass().getResource("stylesheets.css").toExternalForm());
        window.show();
    }

    public static VBox getLayout() {
        return layout;
    }

    public static Stage getStage() {
        return window;
    }
}
