package planmat.appcore;

import android.app.Activity;

import com.android.volley.Response;

import planmat.datarepresentation.*;
import planmat.externaldata.ServerAccessor;
import planmat.externaldata.SIGAAServerAccessor;
import planmat.internaldata.UserPrefs;

public class ApplicationCore {

    private static ApplicationCore instance = new ApplicationCore();
    private ServerAccessor serverAccessor;
    private Recommender recommender;

    private ApplicationCore() {
        this.serverAccessor = new SIGAAServerAccessor();
        this.recommender = new SIGAARecommender();
    }

    public ServerAccessor getServerAccessor() {
        return serverAccessor;
    }

    public Recommender getRecommender() {
        return recommender;
    }

    public static ApplicationCore getInstance() {
        return instance;
    }

}
