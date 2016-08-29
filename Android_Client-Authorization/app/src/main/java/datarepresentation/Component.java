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

    public Component(String name, String code, ArrayList<Component> prerequisite, ArrayList<Component> corequisite) {
        this.name = name;
        this.code = code;
        this.prerequisite = prerequisite;
        this.corequisite = corequisite;
    }
}
