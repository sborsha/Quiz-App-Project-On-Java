import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserJson {
    public static void main(String[] args) throws IOException, ParseException {
        WriteJsonObj ("admin", "1234", "admin");
        WriteJsonObj ("salman", "1234", "student");

    }
    public static void WriteJsonObj(String username,String password, String role) throws IOException, ParseException {
        String filename="./src/main/resources/users.json";
        JSONParser jsonParser=new JSONParser ();
        JSONArray usersArray=(JSONArray) jsonParser.parse(new FileReader (filename));

        JSONObject jsonObject=new JSONObject ();
        jsonObject.put ("username",username);
        jsonObject.put("password", password);
        jsonObject.put("role", role);

        usersArray.add (jsonObject);

        FileWriter fw = new FileWriter (filename);
        fw.write (usersArray.toJSONString ());
        fw.flush ();
        fw.close ();
    }
}
