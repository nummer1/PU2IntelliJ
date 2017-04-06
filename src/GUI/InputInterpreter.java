package GUI;

import ai.api.model.AIResponse;
import AI.TextClientApplication;
import javafx.scene.layout.VBox;

import java.util.HashMap;

/**
 * Created by havardbjornoy on 15/03/2017.
 */
public class InputInterpreter {

    VBox layout = App2.getLayout();
    String switchFromMajor;
    String switchToMajor;
    int semestersStudied;



    private TextClientApplication anna = new TextClientApplication();

    public String interpret(String request) {

        if (!request.trim().isEmpty()) {
            AIResponse response = anna.GetResponse(request);
            String action = response.getResult().getAction();
            boolean actionIncomplete = response.getResult().isActionIncomplete();
            HashMap parameters = response.getResult().getParameters();
            String speech = response.getResult().getFulfillment().getSpeech();

            if (actionIncomplete) {
                return speech;
            }
            else {
                switch (action) {
                    case "make.studyplan":
                        switchFromMajor = parameters.get("Switch-from-major").toString();
                        switchFromMajor = switchFromMajor.replace("\"", "");
                        switchToMajor = parameters.get("Switch-to-major").toString();
                        String sem = parameters.get("Semesters-studied").toString();
                        sem = sem.replace("\"", "");
                        semestersStudied = Integer.parseInt(sem);
                        ChatBoxLogic.showUserCoursesFrom(switchFromMajor, semestersStudied);
                        layout.getChildren().get(2).setVisible(true);

                    case "get more information about TDT4240": // return link to webpage to course
                        //TODO:
                        break;
                }
            }
            return speech;

        } else {
            return "You have to write a question or request below";
        }
    }

    public String getSwitchFromMajor() {
        return switchFromMajor;
    }

    public String getSwitchToMajor() {
        return switchToMajor;
    }

    public int getSemestersStudied() {
        return semestersStudied;
    }
}
