package planmat.appcore;

import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public interface Recommender {

    String checkSemester(UserPrefs.Semester semester);
    UserPrefs.Semester recommendSemester(UserPrefs prefs, int s);

}
