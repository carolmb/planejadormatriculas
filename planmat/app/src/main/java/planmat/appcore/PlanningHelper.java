package planmat.appcore;

import java.util.Iterator;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.datarepresentation.StatList;
import planmat.internaldata.UserPrefs;

/**
 * Created by Ana Caroline on 13/11/2016.
 */
public class PlanningHelper {

    public static UserPrefs.Semester recommendSemesterNormal(UserPrefs prefs, int s) {
        UserPrefs.Semester semester = new UserPrefs.Semester();
        Requirements req = ApplicationCore.getInstance().getRequirements(prefs.getRequirementsID());
        // Adicionar todos os componentes que deviam ter sido cursados até agora
        for(int i = 1; i < s + 1 && i < req.getSemesters().size(); i++) {
            Semester reqSemester = req.getSemesters().get(i);
            for(Component comp : reqSemester.getComponents()) {
                semester.getComponents().add(comp.getCode());
            }
        }
        // Retirar os que já foram cursados
        for(int i = 0; i < s && i < prefs.getPlanning().size(); i++) {
            UserPrefs.Semester userSemester = prefs.getPlanning().get(i);
            for (String code : userSemester.getComponents()) {
                semester.getComponents().remove(code);
            }
        }
        return semester;
    }

    public static UserPrefs.Semester recommendSemesterByWorkload(UserPrefs prefs, int s, int maxWorkload) {
        UserPrefs.Semester semester = recommendSemesterNormal(prefs, s);

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

    public static UserPrefs.Semester recommendSemesterBySuccesses(UserPrefs prefs, int s, float minRate) {
        UserPrefs.Semester semester = recommendSemesterNormal(prefs, s);

        float rate = 1;
        Iterator<String> it = semester.getComponents().iterator();
        while(it.hasNext()) {
            StatList statList = ApplicationCore.getInstance().getStatList(it.next());
            if(rate * statList.getSuccessRate() < minRate){
                it.remove();
            } else {
                rate *= statList.getSuccessRate();
            }
        }
        return semester;
    }

    public static int findTotalWorkload(UserPrefs prefs, int s) {
        int wl = 0;
        Requirements req = ApplicationCore.getInstance().getRequirements(prefs.getRequirementsID());

        for(int i = 0; i < s && i < prefs.getPlanning().size(); i++) {
            UserPrefs.Semester userSemester = prefs.getPlanning().get(i);
            for (String code : userSemester.getComponents()) {
                Component comp = req.getComponent(code);
                wl += comp.getWorkload();
            }
        }

        return wl;
    }
}
