package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Semester implements Serializable {
    private ArrayList<Component> components;

    public Semester(ArrayList<Component> components) {
        this.components = components;
    }
}