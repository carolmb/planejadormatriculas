package planmat.appcore;

import planmat.datarepresentation.Component;
import planmat.internaldata.UserPrefs;

/**
 * Created by User on 14/11/2016.
 */
public class CheckSemesterMyWorkload implements CheckSemester {

    static int maxWorkload = 360;

    public String checkSemester(UserPrefs.Semester semester) {
        int sum = 0;
        for (String code : semester.getComponents()) {
            Component component = ApplicationCore.getInstance().getComponent(code);
            sum+=component.getWorkload();
        }

        if(sum < maxWorkload) {
            return null;
        } else {
            return "Sua carga horária está muito alta!";
        }
    }
}
