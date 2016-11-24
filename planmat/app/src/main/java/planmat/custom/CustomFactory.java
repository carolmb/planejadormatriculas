package planmat.custom;

import planmat.appcore.Recommender;
import planmat.datarepresentation.DataConverter;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Ana Caroline on 15/11/2016.
 */
public class CustomFactory {

    public ServerAccessor getServerAccessor() { return new FakeServerAccessor(); }

    public DataConverter getDataConverter() {
        return new SIGAADataConverter();
    }

    public Recommender getRecommender() {
        return new RecommenderByWorkload();
    }

}
