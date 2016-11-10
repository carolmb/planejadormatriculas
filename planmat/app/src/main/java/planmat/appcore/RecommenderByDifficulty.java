package planmat.appcore;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public class RecommenderByDifficulty implements Recommender {

    public String checkSemester(UserPrefs.Semester semester) {
        float rate = 1;
        for (String code : semester.getComponents()) {
            Component component = ApplicationCore.getInstance().getComponent(code);
            rate *= component.getSuccessRate();
        }
        if (rate < 0.5) {
            return null;
        }
        else {
            return "Tem muitas disciplinas difíceis num semestre só!";
        }
    }

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
