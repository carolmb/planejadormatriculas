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
        this.code = code;
        this.name = name;
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

    public void setClassList(ClassList list) {
        this.classList = list;
    }

    public String toString() {
        return code + " - " + name;
    }

    public float getSuccessRate() {
        float mean = 0;
        for (ClassList.Entry entry : classList.getEntries()) {
            float total = entry.getFails() + entry.getQuits() + entry.getSuccesses();
            mean += entry.getSuccesses() / total;
        }
        return mean / classList.getEntries().size();
    }

}
