package planmat.appcore;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.StatList;
import planmat.internaldata.UserPrefs;

/**
 * Created by Ana Caroline on 13/11/2016.
 */
public class CheckSemester {
    public static String checkSemesterSuccesses(UserPrefs.Semester semester) {
        float rate = 1;
        for (String code : semester.getComponents()) {
            StatList stat = ApplicationCore.getInstance().getStatList(code);
            rate *= stat.getSuccessRate();
        }
        if (rate < 0.5) {
            return null;
        }
        else {
            return "Tem muitas disciplinas difíceis num semestre só!";
        }
    }

    public static String checkSemesterByWorkload(UserPrefs.Semester semester, int maxWorkload) {
        int sum = 0;
        for (String code : semester.getComponents()) {
            Component component = ApplicationCore.getInstance().getComponent(code);
            sum += component.getWorkload();
        }

        if (sum < maxWorkload) {
            return null;
        } else {
            return "Sua carga horária está muito alta!";
        }
    }
}
