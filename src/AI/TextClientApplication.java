package AI;

/**
 * Created by havardbjornoy on 23/03/2017.
 */

import ai.api.AIConfiguration;
import ai.api.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

public class TextClientApplication {

    AIDataService dataService;

    /**
     * Building the clientside
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
    /**
     * Default exit code in case of error
     */
    private static final int ERROR_EXIT_CODE = 1;


    public AIResponse GetResponse(String question) {
        try {
            AIRequest request = new AIRequest(question);

            AIResponse response = dataService.request(request);

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
     *
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
