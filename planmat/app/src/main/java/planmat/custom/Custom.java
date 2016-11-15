package planmat.custom;

import planmat.appcore.Recommender;
import planmat.datarepresentation.DataConverter;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Ana Caroline on 15/11/2016.
 */
public class Custom {
    ServerAccessor serverAccessor;
    DataConverter dataConverter;
    Recommender recommender;

    public Custom() {
        serverAccessor = new SIGAAServerAccessor();
        dataConverter = new SIGAADataConverter();
        recommender = new RecommenderByWorkload();
    }

    public ServerAccessor getServerAccessor() {
        return serverAccessor;
    }

    public DataConverter getDataConverter() {
        return dataConverter;
    }

    public Recommender getRecommender() {
        return recommender;
    }
}
