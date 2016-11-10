package planmat.internaldata;

import java.io.Serializable;
import java.util.ArrayList;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;

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
    private String userID;
    private String majorID;
    private String requirementsID;
    private int currentSemester;
    private ArrayList<Semester> planning;

    public UserPrefs(String name, String userID, String majorID, String requirementsID, int currentSemester) {
        this.name = name;
        this.userID = userID;
        this.majorID = majorID;
        this.requirementsID = requirementsID;
        this.currentSemester = currentSemester;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getMajorID() {
        return majorID;
    }

    public int getCurrentSemester() { return currentSemester; }

    public String getRequirementsID() {
        return requirementsID;
    }

    public ArrayList<Semester> getPlanning() {
        return planning;
    }

    public void setDefaultPlanning(Requirements requirements) {
        planning = new ArrayList<>();
        for (planmat.datarepresentation.Semester reqSemester : requirements.getSemesters()) {
            Semester userSemester = new Semester();
            for(Component comp : reqSemester.getComponents()) {
                userSemester.getComponents().add(comp.getCode());
            }
            planning.add(userSemester);
        }
    }

}
