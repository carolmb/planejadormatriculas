package planmat.datarepresentation;

import android.util.Log;

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

    public ArrayList<Component> getComponents() {
        return components;
    }

    public String[] getComponentsToString() {
        if(components == null) {
            Log.e("COMPONENTS", "components is null");
            return null;
        }
        String[] strings = new String[components.size()];
        for(int i = 0; i < components.size(); i++) {
            strings[i] = components.get(i).toString();
        }
        return strings;
    }

}