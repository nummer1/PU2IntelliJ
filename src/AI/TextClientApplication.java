package AI;

/**
 * Created by havardbjornoy on 23/02/2017.
 */

/***********************************************************************************************************************
 *
 * API.AI Java SDK - client-side libraries for API.AI
 * =================================================
 *
 * Copyright (C) 2016 by Speaktoit, Inc. (https://www.speaktoit.com) https://www.api.ai
 *
 * *********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 ***********************************************************************************************************************/


        import ai.api.AIConfiguration;
        import ai.api.AIDataService;
        import ai.api.model.AIRequest;
        import ai.api.model.AIResponse;

public class TextClientApplication {

    AIDataService dataService;


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
