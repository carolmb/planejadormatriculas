package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 03/09/2016.
 */
public class Requirements implements Serializable {

    private int id;
    private ArrayList<Semester> semesters;
    private HashMap<String, Component> map;

    public Requirements(int id, ArrayList<Semester> semesters) {
        this.id = id;
        this.semesters = semesters;
        createComponentMap();
    }

    public int getID() {
        return id;
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
