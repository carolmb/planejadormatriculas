package datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2016.
 * Representa o currículo de um curso.
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
