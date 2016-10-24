package planmat.appcore;

import planmat.externaldata.SIGAAServerAccessor;

/**
 * Created by User on 23/10/2016.
 */
public class MyAppCore extends ApplicationCore{

    private MyAppCore instance = new MyAppCore();

    private MyAppCore() {
        super(new SIGAAServerAccessor());
    }

    public MyAppCore getInstance() {
        return instance;
    }

    // new methods here
}
