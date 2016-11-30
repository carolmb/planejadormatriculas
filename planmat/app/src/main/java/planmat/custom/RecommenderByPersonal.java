package planmat.custom;

import planmat.appcore.SemesterCheckHelper;
import planmat.appcore.PlanningHelper;
import planmat.appcore.Recommender;
import planmat.internaldata.UserPrefs;
import planmat.internaldata.UserPrefsAccessor;

import java.lang.String;

/**
 * Created by feisabel on 11/29/16.
 */
public class RecommenderByPersonal extends Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        UserPrefs prefs = UserPrefsAccessor.getInstance().getPrefs();
        int s = prefs.getPlanning().size();
        int workload;
        if(s > 1) {
            workload = PlanningHelper.findTotalWorkload(prefs, s);
            workload /= s;
        }
        else
            workload = 360;
        return SemesterCheckHelper.checkSemesterByWorkload(semester, workload);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        int workload;
        if(s>1) {
            workload = PlanningHelper.findTotalWorkload(prefs, s);
            workload /= s-1;
        }
        else
            workload = 360;
        return PlanningHelper.recommendSemesterByWorkload(prefs, s, workload);
    }

}
