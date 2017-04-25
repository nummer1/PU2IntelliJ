package GUI;

import Algorithm.Course;
import Algorithm.DbCom;
import ai.api.model.AIResponse;
import AI.TextClientApplication;
import javafx.scene.layout.VBox;

import java.util.HashMap;

/**
 * Created by havardbjornoy on 15/03/2017.
 */
public class InputInterpreter {

    VBox layout = App2.getLayout();
    private static String switchFromMajor;
    private static String switchToMajor;
    int semestersStudied;
    DbCom db = new DbCom();




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
                        switchFromMajor = parameters.get("Switch-from-major").toString();   //  "Switch-from-major" is a courseCode
                        switchFromMajor = switchFromMajor.replace("\"", "");
                        switchToMajor = parameters.get("Switch-to-major").toString();       //  "Switch-to-major" is a courseCode
                        String sem = parameters.get("Semesters-studied").toString();
                        sem = sem.replace("\"", "");
                        semestersStudied = Integer.parseInt(sem);
                        ChatBoxLogic.showUserCoursesFrom(switchFromMajor, semestersStudied);
                        layout.getChildren().get(2).setVisible(true);
                        App2.getSubmitBtn().setVisible(true);

                    case "get.URL": // return link to webpage to course
                        String courseCode = parameters.get("Course").toString().replace('\"',' ').trim();
                        Course course = db.getCourseSingle(courseCode);
                        String url = course.getURL();
                        return speech + " " + url;
                }
            }
            return speech;

        } else {
            return "You have to write a question or request below";
        }
    }

    public static String getSwitchFromMajor() {
        return switchFromMajor;
    }

    public static String getSwitchToMajor() {
        return switchToMajor;
    }

    public int getSemestersStudied() {
        return semestersStudied;
    }
}
