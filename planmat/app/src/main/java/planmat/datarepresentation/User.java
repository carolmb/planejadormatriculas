package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 17/09/2016.
 */
public class User implements Serializable {

    private String id;
    private String name;
    private String userName;

    public User(String id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() { return userName; }

}
