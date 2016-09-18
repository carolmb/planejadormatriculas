package planmat.internaldata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefs implements Serializable {

    public static class Semester {
        private ArrayList<String> componentIDs;

        public Semester(ArrayList<String> componentIDs) {
            this.componentIDs = componentIDs;
        }

        public ArrayList<String> getComponentIDs() {
            return componentIDs;
        }
    }

    private String name;
    private int userID;
    private int majorID;
    private int requirementsID;
    private ArrayList<Semester> semesters;

    public UserPrefs(String name, int userID, int majorID, int requirementsID) {
        this.name = name;
        this.userID = userID;
        this.majorID = majorID;
        this.requirementsID = requirementsID;
        this.semesters = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getUserID() {
        return userID;
    }

    public int getMajorID() {
        return majorID;
    }

    public int getRequirementsID() {
        return requirementsID;
    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

}
