package planmat.custom;

import planmat.appcore.CheckSemester;
import planmat.appcore.PlanningRecommender;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by Ana Caroline on 15/11/2016.
 */
public class RecommenderBySucesses extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        return CheckSemester.checkSemesterSuccesses(semester);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningRecommender.recommendSemesterBySuccesses(prefs, s, 0.5f);
    }
}
