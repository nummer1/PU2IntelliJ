package AI;

/**
 * Created by havardbjornoy on 23/02/2017.
 *
 * Made for the sole purpose of testing the TextClientApplication
 */
import AI.TextClientApplication;
import ai.api.model.AIResponse;

import java.util.Scanner;

public class Tester {  //Class that is gonna take use of TextClientApplication

    public static void main(String[] args) {
        TextClientApplication ai = new TextClientApplication();
        Scanner scanner = new Scanner(System.in);
        String q = scanner.nextLine();
        AIResponse response = ai.GetResponse(q);

        while (response.getResult().isActionIncomplete()) {
            System.out.println(response.getResult().getFulfillment().getSpeech());
            q = scanner.nextLine();
            response = ai.GetResponse(q);
            System.out.println(response.getResult().getParameters().toString());
        }
        System.out.println(response.getResult().getAction());
        System.out.println(response.getResult().getParameters());
    }

}
