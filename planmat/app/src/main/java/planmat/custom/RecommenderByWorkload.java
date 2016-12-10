package planmat.custom;

import planmat.appcore.SemesterCheckHelper;
import planmat.appcore.PlanningHelper;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by User on 15/11/2016.
 */
public class RecommenderByWorkload extends Recommender {

    static int workload = 400;

    public String checkSemester(UserPrefs.Semester semester) {
        return SemesterCheckHelper.checkSemesterByWorkload(semester, workload);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningHelper.recommendSemesterByWorkload(prefs, s, workload);
    }
}
