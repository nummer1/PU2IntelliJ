package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage window;
    private static BorderPane layout = new BorderPane();

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return window;
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("ANNABot");

        TopSection topSection = new TopSection();

        layout.getStyleClass().add("main-background");
        layout.setTop(topSection.getTopSection());

        if (App2.getStage() != null) {
            App2.getStage().getScene().setRoot(new Region());
            App2.getLayout().getChildren().clear();
        }
        window.setScene(new Scene(layout));
        window.setFullScreen(true);
        layout.getStylesheets().add(getClass().getResource("stylesheets.css").toExternalForm());
        window.show();
    }

    public static BorderPane getLayout() {
        return layout;
    }
}
