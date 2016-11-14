package planmat.appcore;

import java.util.ArrayList;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public abstract class Recommender {

    public abstract String checkSemester(UserPrefs.Semester semester);

    public abstract UserPrefs.Semester recommendSemester(UserPrefs prefs, int s);

    public ArrayList<UserPrefs.Semester> getDefaultPlanning(Requirements requirements) {
        ArrayList<UserPrefs.Semester> planning = new ArrayList<>();
        for (planmat.datarepresentation.Semester reqSemester : requirements.getSemesters()) {
            UserPrefs.Semester userSemester = new UserPrefs.Semester();
            for(Component comp : reqSemester.getComponents()) {
                userSemester.getComponents().add(comp.getCode());
            }
            planning.add(userSemester);
        }
        planning.remove(0);
        return planning;
    }
}
