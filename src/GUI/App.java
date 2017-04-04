package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {

    private Stage window;
    private static BorderPane layout = new BorderPane();


    public static void main(String[] args) {
        launch(args);
    }

    public Stage getStage() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ANNABot");

        TopSection topSection = new TopSection();

        layout.setStyle("-fx-background-color: #fff9c4;");
        layout.setTop(topSection.getTopSection());

        window.setScene(new Scene(layout, 800, 400));
        window.show();
    }

    public static BorderPane getLayout() {
        return layout;
    }
}
