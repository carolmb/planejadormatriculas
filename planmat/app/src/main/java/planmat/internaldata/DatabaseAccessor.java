package planmat.internaldata;

import android.app.Activity;
import android.content.Context;

import planmat.custom.CustomFactory;
import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.StatList;
import planmat.datarepresentation.User;
import planmat.externaldata.ServerAccessor;

/**
 * Created by Luisa on 24/11/2016.
 */
public class DatabaseAccessor {

    private static DatabaseAccessor instance = new DatabaseAccessor();

    private ServerAccessor serverAccessor;
    private DatabaseHandler databaseHandler;

    private DatabaseAccessor() {
        serverAccessor = CustomFactory.getInstance().getServerAccessor();
    }

    public static DatabaseAccessor getInstance() {
        return instance;
    }

    public void storeRequirements(Requirements req, Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        // TODO: salvar no banco de dados interno os componentes
        // TODO: substituir os métodos marcados abaixo pelo acesso ao BD
    }

    public void login(Activity activity) {
        serverAccessor.login(activity);
    }

    public User getUser() {
        return serverAccessor.getUser();
    }

    public IDList getMajorList() {
        return serverAccessor.getMajorList();
    }

    public IDList getRequirementsList(String code) {
        return serverAccessor.getRequirementsList(code);
    }

    public Requirements getRequirements(String code) {
        // TODO: pegar do BD
        return serverAccessor.getRequirements(code);
    }

    public ClassList getClassList(String code) {
        // TODO: pegar do BD
        return serverAccessor.getClassList(code);
    }

    public StatList getStatList(String code) {
        // TODO: pegar do BD
        return serverAccessor.getStatList(code);
    }

}
