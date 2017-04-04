package Algorithm;

import java.io.IOException;
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
        courses = dbCom.getCourses();
        // String s = "'[";
        System.out.println("before for-loop");

        for (int i = 0; i < courses.size(); i++) {
            System.out.println("inside forloop, i= " + i);
            System.out.println();
            String[] course = courses.get(i).split(" ");
            System.out.println(course[0]);
            System.out.println(course[1]);
            String courseCode = course[0];
            String courseName = course[1];

            String s = "[{\"values\": \"" + courseCode + "\",\"synonyms\": [\"" + courseCode + "\", \"" + courseName + "\"]}]";

            try {
                Runtime.getRuntime().exec("/bin/bash -c curl -i -X POST -H \"Content-Type:application/json\" -H \"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\" -d " + s + "'https://console.api.ai/api-client/#/agent/d25c77d3-dd43-4d57-9e4a-16f47a40dee4/editEntity/e8fafbbb-2be7-4305-9bb5-4104a1513168'");
                System.out.print("it worked?!?");                                                                                                                                               // 'https://api.api.ai/v1/entities/cdc72cfd-78da-41cb-8af4-e4237bd93101/entries?v=20150910'
            }
            catch (IOException e){
                e.printStackTrace();
            }

            /*
            if (i != courses.size() - 1) {
                s += ",";
            }
            */
        }
    }

    public static void main(String[] args) {
        AIStringBuilder a = new AIStringBuilder();
        a.MakeString();
        /*
        try {
            Runtime.getRuntime().exec("/bin/bash -c curl -i -X POST -H \"Content-Type:application/json\" -H \"Authorization:Bearer 9f8a6f4d41834aa0bcbec4e763168c89\" -d " + s + "'https://console.api.ai/api-client/#/agent/d25c77d3-dd43-4d57-9e4a-16f47a40dee4/editEntity/e8fafbbb-2be7-4305-9bb5-4104a1513168'");
            System.out.print("it worked?!?");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        */
    }
}
