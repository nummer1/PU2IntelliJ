package GUI; /**
 * Created by andreaswilhelmflatt on 19.02.2017.
 */

import Algorithm.Course;
import Algorithm.DbCom;
import Algorithm.Selector;
import Algorithm.StudyPlan;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App2 extends Application {

    private static Stage window;
    private static VBox layout = new VBox(2);
    private MidSection midSection;
    private DbCom dbCom = new DbCom();
    private static Button submitBtn;

    public static void main(String[] args) {
        launch(args);
    }

    public static Button getSubmitBtn() {return submitBtn;}

    @Override
    public void start(Stage primaryStage) {
        layout.getChildren().clear();
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

        DbCom courseConverter = new DbCom();

        submitBtn = new Button("Submit");
        submitBtn.getStyleClass().add("change-interface-btn");
        submitBtn.setVisible(false); // SET THIS TO TRUE WHEN REQUIREMENTS ARE FULFILLED
        submitBtn.setOnAction(event -> {
            midSection = new MidSection();
            layout.getChildren().remove(MidSection.getCoursePlan());
            layout.getChildren().add(midSection.generateMidSection(InputInterpreter.getSwitchFromMajor().toString(), InputInterpreter.getSwitchToMajor().replace("\"", "").trim().toString()));
            submitBtn.setDisable(true);
        });

        layout.getStyleClass().add("main-background");

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
