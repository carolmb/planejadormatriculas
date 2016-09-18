package planmat.internaldata;

/**
 * Created by Luisa on 18/09/2016.
 */
public class UserPrefsAccessor {

    private static UserPrefsAccessor instance = new UserPrefsAccessor();

    private UserPrefsAccessor() {}

    public static UserPrefsAccessor getInstance() {
        return instance;
    }

    public UserPrefs loadUserPrefs(int userID) {
        //TODO: carregar arquivo da memória do celular
        return null;
    }

    public void storeUserPrefs(UserPrefs userPrefs) {
        //TODO: salvar arquivo usando o userPrefs.getID() como nome
    }

}
