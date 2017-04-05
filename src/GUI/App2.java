package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
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
        changeInterfaceBtn.setStyle("-fx-background-color: \n" +
                "        #a6b5c9,\n" +
                "        linear-gradient(#303842 0%, #3e5577 20%, #375074 100%),\n" +
                "        linear-gradient(#768aa5 0%, #849cbb 5%, #5877a2 50%, #486a9a 51%, #4a6c9b 100%);\n" +
                "    -fx-background-insets: 0 0 -1 0,0,1;\n" +
                "    -fx-background-radius: 5,5,4;\n" +
                "    -fx-padding: 7 30 7 30;\n" +
                "    -fx-text-fill: #242d35;\n" +
                "    -fx-font-family: \"Helvetica\";\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-text-fill: white;");

        changeInterfaceBtn.setOnAction(event -> {
            App app = new App();
            app.start(window);
        });

        ChatBox chatBox = new ChatBox();
        layout.setAlignment(Pos.CENTER);

        layout.setStyle("-fx-background-color: #fff9c4;");
        layout.getChildren().addAll(changeInterfaceBtn, chatBox.getChatBox());

        ChatBoxLogic.showUserCoursesFrom("Datateknologi", 2);

        window.setScene(new Scene(layout, 800, 400));
        window.show();
    }

    public static VBox getLayout() {
        return layout;
    }
}
