package planmat.custom;

import planmat.appcore.CheckSemester;
import planmat.appcore.PlanningRecommender;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;

/**
 * Created by User on 15/11/2016.
 */
public class RecommenderByWorkload extends Recommender {

    static int workload = 400;

    public String checkSemester(UserPrefs.Semester semester) {
        return CheckSemester.checkSemesterByWorkload(semester, workload);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningRecommender.recommendSemesterByWorkload(prefs, s, workload);
    }
}
