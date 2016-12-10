package planmat.custom;

import planmat.appcore.SemesterCheckHelper;
import planmat.appcore.PlanningHelper;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by Ana Caroline on 15/11/2016.
 */
public class RecommenderBySucesses extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        return SemesterCheckHelper.checkSemesterSuccesses(semester);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningHelper.recommendSemesterBySuccesses(prefs, s, 0.5f);
    }
}
