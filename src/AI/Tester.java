package AI;

/**
 * Created by havardbjornoy on 23/02/2017.
 */
import AI.TextClientApplication;
import ai.api.model.AIResponse;

public class Tester {  //Class that is gonna take use of TextClientApplication

    public static void main(String[] args) {
        TextClientApplication ai = new TextClientApplication();
        String q = "i wanna change major to comtech";
        AIResponse response = ai.GetResponse(q);
        System.out.println(response.getResult().getParameters().toString());
    }

}
