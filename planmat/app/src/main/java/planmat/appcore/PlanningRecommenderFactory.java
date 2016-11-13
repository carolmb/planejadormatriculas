package planmat.appcore;

/**
 * Created by Ana Caroline on 13/11/2016.
 */
public class PlanningRecommenderFactory {

    private static PlanningRecommenderFactory instance;

    public static int BYNORMAL; // seguindo a grade esperada

    private PlanningRecommenderFactory() {}

    public static PlanningRecommenderFactory getInstance() {
        if(instance == null) {
            instance = new PlanningRecommenderFactory();
        }
        return instance;
    }

    public PlanningRecommender getPlanningRecommender(int type) {
        PlanningRecommender planningRecommender = null;
        if(type == BYNORMAL) {
            planningRecommender = new PlanningRecommenderByNormal();
        }
        return planningRecommender;
    }
}
