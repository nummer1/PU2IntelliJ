/**
 * Created by kasparov on 20.02.17.
 */
import com.google.gson.Gson;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import java.io.FileReader;
import java.lang.reflect.Field;

//returns map with requested course
public class ApiAiRes {

  public static void main(String[] args) throws Exception {
    Gson gson = new Gson();
    ApiAi test = gson.fromJson(new FileReader("jsonExample.json"), ApiAi.class);
    System.out.println(test.toString());
    System.out.println(gson.toJson(test));
    System.out.println(test.result.get("action"));
  }
}

class ApiAi {
  String id;
  String timestamp;
  String lang;
  Result result;
}

class Result extends HashMap {
    String source;
    String resolvedQuery;
    String action;
    Boolean actionIncomplete;
    Map<String, String> parameters;
    List contexts;
    Map<String, String> metadata;
    Fulfillment fulfillment;
    Integer score;
    Map<String, Object> status;
    String sessionId;
}

class Fulfillment extends HashMap{ 
    String speech;
    List<Object> messages;
}

class ApiAiFieldNamingStrategy implements FieldNamingStrategy {
    //Translates the Java field name into its JSON element name representation.
    @Override
    public String translateName(Field field) {
        String name = field.getName();
        return name;
    }
}
