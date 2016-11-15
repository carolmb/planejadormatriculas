package planmat.custom;

import planmat.appcore.CheckSemester;
import planmat.appcore.PlanningRecommender;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public class RecommenderByDifficulty extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        return CheckSemester.checkSemesterSuccesses(semester);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningRecommender.recommendSemesterNormal(prefs, s);
    }

}
