package planmat.appcore;

import planmat.internaldata.UserPrefs;

/**
 * Created by Ana Caroline on 13/11/2016.
 */
public interface PlanningRecommender {
    UserPrefs.Semester recommendSemester(UserPrefs prefs, int s);
}
