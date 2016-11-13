package planmat.appcore;

import planmat.datarepresentation.StatList;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luísa on 13/11/2016.
 */
public class CheckSemesterBySuccesses implements CheckSemester {

    public String checkSemester(UserPrefs.Semester semester) {
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
}
