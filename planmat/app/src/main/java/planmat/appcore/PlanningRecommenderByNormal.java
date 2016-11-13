package planmat.appcore;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luísa on 13/11/2016.
 */
public class PlanningRecommenderByNormal implements PlanningRecommender {
    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        UserPrefs.Semester semester = new UserPrefs.Semester();
        Requirements req = ApplicationCore.getInstance().getRequirements(prefs.getRequirementsID());
        // Adicionar todos os componentes que deviam ter sido cursados até agora
        for(int i = 0; i < s; i++) {
            Semester reqSemester = req.getSemesters().get(i);
            for(Component comp : reqSemester.getComponents()) {
                semester.getComponents().add(comp.getCode());
            }
        }
        // Retirar os que já foram cursados
        for(int i = 0; i < s; i++) {
            UserPrefs.Semester userSemester = prefs.getPlanning().get(i);
            for (String code : userSemester.getComponents()) {
                semester.getComponents().remove(code);
            }
        }
        return semester;
    }
}
