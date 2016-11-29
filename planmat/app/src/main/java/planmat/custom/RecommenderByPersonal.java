package planmat.custom;

import planmat.appcore.CheckSemester;
import planmat.appcore.PlanningRecommender;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;
import android.util.Log;
import java.lang.String;

/**
 * Created by feisabel on 11/29/16.
 */
public class RecommenderByPersonal extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        UserPrefs prefs = UserPrefsAccessor.prefs;
        int s = prefs.getPlanning().size();
        int workload;
        if(s > 1) {
            workload = PlanningRecommender.findTotalWorkload(prefs, s);
            workload /= s;
        }
        else
            workload = 360;
        return CheckSemester.checkSemesterByWorkload(semester, workload);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        int workload;
        if(s>1) {
            workload = PlanningRecommender.findTotalWorkload(prefs, s);
            workload /= s-1;
        }
        else
            workload = 360;
        return PlanningRecommender.recommendSemesterByWorkload(prefs, s, workload);
    }
}
