package Algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.Runtime;

/**
 * Created by havardbjornoy on 27/03/2017.
 */
public class AIStringBuilder {

    private DbCom dbCom = new DbCom();


    private void MakeString() {
        ArrayList<String> courses;
        courses = dbCom.getCoursesAsString();
        System.out.println("before for-loop");

        for (int i = 0; i < courses.size(); i += 100) {
            System.out.println("inside forloop, i= " + i);
            System.out.println();
            String[] course = courses.get(i).split(" ");
            System.out.println(course[0]);
            System.out.println(course[1]);
            String courseCode = course[0];
            String courseName = course[1];

            String s = "'[{\"values\": \"" + courseCode + "\",\"synonyms\": [\"" + courseCode + "\", \"" + courseName + "\"]}]'";
            String[] command = {"/bin/bash", "curl", "-i", "-X", "POST", "-H", "\"Content-Type:application/json\",", "-H", "\"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\"", "-d", s, "'https://api.api.ai/v1/entities/Subject/entries'"};


            try {
                // Process proc = new ProcessBuilder(command).start();         // "curl -i -X POST -H \"Content-Type:application/json\" -H \"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\" -d '" + s + "' 'https://api.api.ai/v1/entities/Subject/entries'"
                String[] ccc = {"/bin/bash","cd", "Github"}; //hvordan teste hvordan det funker?
                Process p = new ProcessBuilder(command).start();
                InputStream is = p.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = "";
                System.out.println(line);
                while((line = br.readLine()) != null) {
                    System.out.println("Inside WHILE");
                    System.out.println(line);
                }

                // Runtime.getRuntime().exec(command);
                System.out.println(s);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //Hvordan skal jeg finne ut hvordan den tolker input?

    public static void main(String[] args) {


        AIStringBuilder a = new AIStringBuilder();
        a.MakeString();
        /*
        try {
            String s = "'[{\"values\": \"" + courseCode + "\",\"synonyms\": [\"" + courseCode + "\", \"" + courseName + "\"]}]'";
            Runtime.getRuntime().exec("/bin/bash -c curl -i -X POST -H \"Content-Type:application/json\" -H \"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\" -d " + s + "'https://console.api.ai/api-client/#/agent/d25c77d3-dd43-4d57-9e4a-16f47a40dee4/editEntity/e8fafbbb-2be7-4305-9bb5-4104a1513168'");
            System.out.print("it worked?!?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
}
