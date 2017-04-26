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

    private static String switchFromMajor;
    private static String switchToMajor;
    int semestersStudied;

    VBox layout = App2.getLayout();

    // The DbCom so InputInterpreter have access to database to get access to Course information
    DbCom db = new DbCom();

    // Create a instance of the Textclient, initializes a conversation
    private TextClientApplication anna = new TextClientApplication();

    /**
     * Interpret function take action when the intent of the user is made clear. This way one can return a textual
     * answer, fetch information from the database and also manipulate GUI to show relevant information.
     *
     * @param request can be a question, response or answer from the user
     * @return return the response from anna the TextClientApplication
     */
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
                // If action is completed then we want to take action on the information.
                // We have different actions for different intents from the user, and they execute different code.
                switch (action) {
                    // this case shows the courses you should have taken to pass the choosen Switch-to-Major and
                    // mark off the subject you probably have taken.
                    case "make.studyplan":
                        switchFromMajor = parameters.get("Switch-from-major").toString(); // "Switch-from-major" is a courseCode
                        switchFromMajor = switchFromMajor.replace("\"", "");
                        switchToMajor = parameters.get("Switch-to-major").toString(); // "Switch-to-major" is a courseCode
                        String sem = parameters.get("Semesters-studied").toString();
                        sem = sem.replace("\"", "");
                        semestersStudied = Integer.parseInt(sem);
                        ChatBoxLogic.showUserCoursesFrom(switchFromMajor, semestersStudied);
                        layout.getChildren().get(2).setVisible(true);
                        App2.getSubmitBtn().setVisible(true);

                    //this case returns the website of the course
                    case "get.URL":
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
}
