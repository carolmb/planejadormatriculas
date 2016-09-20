package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Requirements implements Serializable {
    private int id;
    private ArrayList<Semester> semesters;

    public Requirements(int id) {
        this.id = id;
        this.semesters = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }
}
