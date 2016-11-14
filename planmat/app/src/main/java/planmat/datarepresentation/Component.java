package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 03/09/2016.
 */
public class Component implements Serializable {

    private String name;
    private String code;

    private int workload;

    public Component(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Component(String code, String name, int workload) {
        this.code = code;
        this.name = name;
        this.workload = workload;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public int getWorkload() { return workload; }

    public String toString() {
        return code + " - " + name;
    }

}
