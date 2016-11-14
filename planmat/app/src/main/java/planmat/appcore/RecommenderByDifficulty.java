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
public class RecommenderByDifficulty extends Recommender{

    public String checkSemester(UserPrefs.Semester semester) {
        return CheckSemester.checkSemesterRate(semester);
    }

    public UserPrefs.Semester recommendSemester(UserPrefs prefs, int s) {
        return PlanningRecommender.recommendSemesterNormal(prefs, s);
    }

}
