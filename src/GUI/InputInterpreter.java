package GUI;

import ai.api.model.AIResponse;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import AI.TextClientApplication;

import java.util.HashMap;

/**
 * Created by havardbjornoy on 15/03/2017.
 */
public class InputInterpreter {

    private TextClientApplication anna = new TextClientApplication();
    private TopSection topSection;

    public InputInterpreter (TopSection topSection) {
        this.topSection = topSection;
    }

    public String interpreter(String request) {


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
                        String switchToMajor = parameters.get("Switch-to-major").toString();
                        Object timeStudied = parameters.get("Switch-from-major"); //Maybe think about modifying @sys.duration and make it include semesters

                        MidSection midSection = new MidSection();
                        midSection.getCoursePlan().getChildren().clear(); // Clear previous studyplan if any.
                        midSection.resetCounts(); // Reset counts for indexing courses.
                        topSection.searchField.getSearchField().setVisible(true); // Set search-field visible.
                        App.getLayout().setCenter(midSection.generateMidSection(switchFromMajor, switchToMajor));  //should have a field for years_studied/semester_studied
                        App.getLayout().setAlignment(App.getLayout().getCenter(), Pos.CENTER);
                        break;
                    case "change.interface": // finn ut ka det heite
                        //TODO:
                        break;
                    case "when is the exam og TDT4100":
                        //TODO:
                        break;
                }
                textArea.setText("Anna: " + speech);
            }
            return speech;

        } else {
            return "You have to write a question or request below"
        }

    }
}
