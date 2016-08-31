package datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2016.
 * Representa uma disciplina do SIGAA.
 */
public class Component {

    private String name;
    private String code;
    private ArrayList<Component> prerequisite;
    private ArrayList<Component> corequisite;

    public Component(String name, String code) {
        this.name = name;
        this.code = code;
        this.prerequisite = new ArrayList<Component>();
        this.corequisite = new ArrayList<Component>();
    }

    public ArrayList<Component> getPrerequisite() {
        return prerequisite;
    }

    public ArrayList<Component> getCorequisite() {
        return corequisite;
    }

}
