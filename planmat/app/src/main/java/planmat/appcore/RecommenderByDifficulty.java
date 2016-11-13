package planmat.appcore;

import java.util.ArrayList;

import planmat.datarepresentation.Component;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
import planmat.datarepresentation.StatList;
import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 24/10/2016.
 */
public class RecommenderByDifficulty {

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

    public ArrayList<UserPrefs.Semester> getDefaultPlanning(Requirements requirements) {
        ArrayList<UserPrefs.Semester> planning = new ArrayList<>();
        for (planmat.datarepresentation.Semester reqSemester : requirements.getSemesters()) {
            UserPrefs.Semester userSemester = new UserPrefs.Semester();
            for(Component comp : reqSemester.getComponents()) {
                userSemester.getComponents().add(comp.getCode());
            }
            planning.add(userSemester);
        }
        planning.remove(0);
        return planning;
    }

}
