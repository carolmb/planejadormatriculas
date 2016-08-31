package internaldata;

/**
 * Created by feisabel on 8/31/16.
 */
public class UserDataManager {
    public UserData load() {

    }

    public void store(UserData data) {

    }

    private static UserDataManager _UserDataManager;

    private UserDataManager() {}

    public static UserDataManager getUserDataManager() {
        if(_UserDataManager == null)
            _UserDataManager = new UserDataManager();
        return _UserDataManager;
    }
}
