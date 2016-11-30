package planmat.custom;

import planmat.appcore.SemesterCheckHelper;
import planmat.appcore.PlanningHelper;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public class RecommenderByDifficulty extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        return SemesterCheckHelper.checkSemesterSuccesses(semester);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningHelper.recommendSemesterNormal(prefs, s);
    }

}
