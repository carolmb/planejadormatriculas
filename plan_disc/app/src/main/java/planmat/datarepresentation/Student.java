package planmat.datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Student {
    private int id;
    private Requirements requirements;
    private ArrayList<ComponentClass> classes;

    public Student(int id, Requirements requirements, ArrayList<ComponentClass> classes) {
        this.id = id;
        this.requirements = requirements;
        this.classes = classes;
    }

    public int getID() {
        return id;
    }

    public Requirements getRequirements() {
        return requirements;
    }

    public ArrayList<ComponentClass> getClasses() {
        return classes;
    }

}
