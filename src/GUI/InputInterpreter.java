package GUI;

import ai.api.model.AIResponse;
import javafx.beans.binding.IntegerBinding;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import AI.TextClientApplication;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by havardbjornoy on 15/03/2017.
 */
public class InputInterpreter {

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
                        String switchFromMajor = parameters.get("Switch-from-major").toString();
                        switchFromMajor = switchFromMajor.replace("\"", "");
                        String sem = parameters.get("semesters-studied").toString();
                        sem = sem.replace("\"", "");
                        int semestersStudied = Integer.parseInt(sem);
                        ChatBoxLogic.showUserCoursesFrom(switchFromMajor, semestersStudied);

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
}
