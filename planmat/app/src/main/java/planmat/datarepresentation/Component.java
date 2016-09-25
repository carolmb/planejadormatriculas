package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Component implements Serializable {
    private String name;
    private String code;

    private ClassList classList;

    public Component(String code, String name, ClassList list) {
        this.name = name;
        this.code = code;
        this.classList = list;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public ClassList getClassList() {
        return classList;
    }

    public String toString() {
        return code + " - " + name;
    }
}
