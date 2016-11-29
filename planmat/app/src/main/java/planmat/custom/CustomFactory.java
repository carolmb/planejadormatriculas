package planmat.custom;

import planmat.appcore.Recommender;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Ana Caroline on 15/11/2016.
 */
public class CustomFactory {

    private static CustomFactory instance = new CustomFactory();
    private CustomFactory() {}

    public static CustomFactory getInstance() {
        return instance;
    }

    public ServerAccessor getServerAccessor() {
        return new FakeServerAccessor();
    }

    public Recommender getRecommender() {
        return new RecommenderByPersonal();
    }

}
