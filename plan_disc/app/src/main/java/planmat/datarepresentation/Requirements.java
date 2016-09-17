package planmat.datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Requirements {
    private int id;
    private ArrayList<Semester> semesters;

    public Requirements(int id) {
        this.id = id;
        this.semesters = new ArrayList<Semester>();
    }

    public int getID() {
        return id;
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }
}
