package planmat.internaldata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefs implements Serializable {

    private String name;

    public UserPrefs(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
