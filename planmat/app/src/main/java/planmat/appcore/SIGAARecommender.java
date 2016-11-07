package planmat.appcore;

import planmat.datarepresentation.Component;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public class SIGAARecommender implements Recommender {

    public boolean checkSemester(UserPrefs.Semester semester) {
        float rate = 1;
        for (String code : semester.getComponents()) {
            Component component = ApplicationCore.getInstance().getServerAccessor().getComponent(code);
            rate *= component.getSuccessRate();
        }
        if (rate < 0.5) {
            return true;
        }
        else {
            return false;
        }
    }
}
