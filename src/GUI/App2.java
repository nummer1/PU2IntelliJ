package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import Algorithm.DbCom;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App2 extends Application {

    private static Stage window;
    private static VBox layout = new VBox(5);
    private MidSection midSection;
    private DbCom dbCom = new DbCom();

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

        Region spacing = new Region();
        spacing.setMinHeight(0);
        spacing.setMaxHeight(0);

        Button submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("change-interface-btn");
        //submitBtn.setVisible(false);
        submitBtn.setOnAction(event -> {
            // how can I get suggested studyplan displayed(with finishedCourses as input...not finished semesters as in Midsection.generateMidsection??)

        });

        layout.setStyle("-fx-background-color: #fff9c4;");

        ChatBoxLogic.showUserCoursesFrom("Datateknologi", 2);
        layout.getChildren().addAll(spacing, changeInterfaceBtn, chatBox.getChatBox(), submitBtn);

        if (App.getStage() != null) {
            App.getStage().getScene().setRoot(new Region());
            App.getLayout().getChildren().clear();
        }

        window.setScene(new Scene(layout));
        window.setFullScreen(true);
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
