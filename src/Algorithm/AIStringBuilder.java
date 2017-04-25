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


    public ArrayList MakeString() {
        Collection<String> courses;
        courses = dbCom.getCoursesAsString();
        System.out.println("before for-loop");
        ArrayList array = new ArrayList<String>();

        for (String course : courses) {
            System.out.println();
            String[] parts = course.split(":");
            System.out.println(parts[0]);
            System.out.println(parts[1]);
            String courseCode = parts[0];
            String courseName = parts[1];
            courseName = replaceNorwegianLetters(courseName);

            String s = "[{\"values\":\"" + courseCode + "\",\"synonyms\":[\"" + courseCode + "\",\"" + courseName + "\"]}]";
            String[] command = {"curl", "-i", "-X", "POST", "-H", "Content-Type:application/json", "-H", "Authorization:Bearer9f8a6f4d41834aa0bcbec4e763168c89", "-d", s, "https://api.api.ai/v1/entities/Course/entries"};
            String fullString = "curl -i -X POST -H \"Content-Type:application/json\" -H Authorization:Bearer9f8a6f4d41834aa0bcbec4e763168c89 -d " + s + " https://api.api.ai/v1/entities/Course/entries";
            System.out.println(fullString);
            array.add(fullString);


            try {
                // "curl -i -X POST -H \"Content-Type:application/json\" -H \"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\" -d '" + s + "' 'https://api.api.ai/v1/entities/Subject/entries'"
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.redirectErrorStream(true);
                final Process process = processBuilder.start();

                InputStream stderr = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(stderr);
                BufferedReader br = new BufferedReader(isr);
                String line = null;


                while ((line = br.readLine()) != null) {
                    System.out.println(line);

                }
                process.waitFor();
                System.out.println("Waiting ...");

                System.out.println("Returned Value :" + process.exitValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
        return array;
    }

    public static String replaceNorwegianLetters(String s) {
        s = s.replace("æ", "ae");
        s = s.replace("ø", "oe");
        s = s.replace("å", "aa");
        return s;
    }

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
