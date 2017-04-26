package AI;

/**
 * Created by havardbjornoy on 23/03/2017.
 *
 * This class is for creating a natural language interpreter. When you create an instant of the TextClientApplication
 * you should use this client for the whole conversation since it can remember what you said earlier and put it into a
 * context. To use the TextClientApplication you simply feed the GetResponse(String) with the users question or answer
 * and this will return a responseObject that has a structure similar to a JSON-structure. From this object you can
 * extract useful information like the intent of the user and the information extracted from the conversation.
 *
 * For more information: https://docs.api.ai/docs/query#response
 *
 */

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class TextClientApplication {

    AIDataService dataService;

    /**
     * Building the clientside. Initializing a conversation
     */
    public TextClientApplication() {
        String args = "44c2085b82f3436da06a12c7008744cb";
        if (args.length() < 1) {
            showHelp("Please specify API key", ERROR_EXIT_CODE);
        }

        AIConfiguration configuration = new AIConfiguration(args);

        dataService = new AIDataService(configuration);

        System.out.print(INPUT_PROMPT);

    }

    private static final String INPUT_PROMPT = "> ";

    //Default exit code in case of error
    private static final int ERROR_EXIT_CODE = 1;

    /**
     * Use GetResponse everytime the user gives a response in the chat.
     * Then use the AIResponse to return choosen info to user.
     *
     * @param request can be a question, response or answer from the user in the conversation
     * @return AIResponse is a object from external library libai, for more information:
     * https://docs.api.ai/docs/query#response.
     */
    public AIResponse GetResponse(String request) {
        try {
            AIRequest request = new AIRequest(request);

            AIResponse response = dataService.request(request);

            //checks if the response experienced an error
            if (response.getStatus().getCode() == 200) {
                return response;
            } else {
                System.err.println(response.getStatus().getErrorDetails());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * Output application usage information to stdout and exit. No return from function.
     *
     * @param errorMessage Extra error message. Would be printed to stderr if not null and not empty.
     */
    private static void showHelp(String errorMessage, int exitCode) {
        if (errorMessage != null && errorMessage.length() > 0) {
            System.err.println(errorMessage);
            System.err.println();
        }

        System.out.println("Usage: APIKEY");
        System.out.println();
        System.out.println("APIKEY  Your unique application key");
        System.out.println("        See https://docs.api.ai/docs/key-concepts for details");
        System.out.println();
        System.exit(exitCode);
    }

    public static void main(String[] args) {

    }
}
