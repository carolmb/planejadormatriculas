package planmat.internaldata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefs implements Serializable {

    public static class Semester implements Serializable {
        private ArrayList<String> components;

        public Semester() {
            this.components = new ArrayList<>();
        }

        public ArrayList<String> getComponents() {
            return components;
        }
    }

    private String name;
    private int userID;
    private int majorID;
    private int requirementsID;
    private ArrayList<Semester> completed;
    private ArrayList<Semester> planning;

    public UserPrefs(String name, int userID, int majorID, int requirementsID) {
        this.name = name;
        this.userID = userID;
        this.majorID = majorID;
        this.requirementsID = requirementsID;
        this.completed = new ArrayList<>();
        this.planning = new ArrayList<>();
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

    public ArrayList<Semester> getPlanning() {
        return planning;
    }

    public ArrayList<Semester> getCompleted() {
        return completed;
    }

}
