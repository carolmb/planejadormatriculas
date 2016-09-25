package planmat.internaldata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefs implements Serializable {

    public static class Semester {
        private String code;
        private ArrayList<String> components;

        public Semester(String code, ArrayList<String> componentIDs) {
            this.components = componentIDs;
        }

        public ArrayList<String> getComponents() {
            return components;
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
