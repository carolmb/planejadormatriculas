package datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2016.
 * Representa o currículo de um curso.
 */
public class Requirements {
    private ArrayList<Semester> semesters;

    public Requirements(ArrayList<Semester> semesters) {
        this.semesters = semesters;
    }
}
