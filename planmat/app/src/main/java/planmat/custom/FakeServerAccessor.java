package planmat.custom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

import planmat.externaldata.AuthorizationRequester;
import planmat.externaldata.DataRequester;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Luisa on 23/11/2016.
 */
public class FakeServerAccessor extends SIGAAServerAccessor {

    private int statCode = 0;

    public JSONArray getJsonArray(String path, String arg) {
        if (path.equals("StatList")) {
            String s = generateStatList();
            try {
                return new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return super.getJsonArray(path, arg);
        }
        return null;
    }

    private String generateStatList() {
        Random r = new Random();
        String s = "[\n";
        int n = r.nextInt(10) + 5;
        s += generateStat(r);
        for(int i = 1; i < n; i++) {
            s += "," + generateStat(r);
        }
        s += "]";
        return s;
    }

    private String generateStat(Random r) {
        return  "  {\n" +
                "    \"ano\": 0,\n" +
                "    \"periodo\": 0,\n" +
                "    \"codigo\": \"" + (statCode++) + "\",\n" +
                "    \"nomeComponente\": \"string\",\n" +
                "    \"codigoComponente\": \"string\",\n" +
                "    \"aprovados\": " + (r.nextInt(20) + 10) + ",\n" +
                "    \"reprovados\": " + r.nextInt(30) + ",\n" +
                "    \"trancados\": " + r.nextInt(10) + "\n" +
                "  }\n";
    }

}
