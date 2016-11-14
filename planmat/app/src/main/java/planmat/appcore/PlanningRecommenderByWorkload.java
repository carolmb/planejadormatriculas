package planmat.appcore;

import java.util.Iterator;

import planmat.datarepresentation.Requirements;
import planmat.internaldata.UserPrefs;

/**
 * Created by User on 14/11/2016.
 */
public class PlanningRecommenderByWorkload implements PlanningRecommender {
    static int maxWorkload = 360;

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        PlanningRecommender pr = new PlanningRecommenderByNormal();
        UserPrefs.Semester semester = pr.recommendSemester(prefs, s + 1);

        int sum = 0;
        Iterator<String> it = semester.getComponents().iterator();
        while(it.hasNext()) {
            int workload = ApplicationCore.getInstance().getComponent(it.next()).getWorkload();
            if(sum + workload > maxWorkload){
                it.remove();
            } else {
                sum+=workload;
            }
        }
        return semester;
    }
}
