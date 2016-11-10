package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 03/09/2016.
 */
public class Requirements implements Serializable {

    private String id;
    private ArrayList<Semester> semesters;
    private HashMap<String, Component> map;

    public Requirements(String id, ArrayList<Semester> semesters) {
        this.id = id;
        this.semesters = semesters;
        createComponentMap();
    }

    public String getID() {
        return id;
    }

    public void putComponent(String code, Component comp) {
        map.put(code, comp);
    }

    public Component getComponent(String code) {
        return map.get(code);
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

    private void createComponentMap() {
        map = new HashMap<>();
        for (Semester s : semesters) {
            for (Component c : s.getComponents()) {
                map.put(c.getCode(), c);
            }
        }
    }

}
