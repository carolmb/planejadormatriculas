package planmat.internaldata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import planmat.custom.CustomFactory;
import planmat.datarepresentation.ClassList;
import planmat.datarepresentation.Component;
import planmat.datarepresentation.IDList;
import planmat.datarepresentation.Requirements;
import planmat.datarepresentation.Semester;
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
        Log.e("DATABASE HANDLER", "requesting instance");
        return instance;
    }

    public void createHandler(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
    }

    public void storeRequirements(Requirements req) {
        int i = 1;
        for(Semester s : req.getSemesters()) {
            for(Component c : s.getComponents()) {
                if (!databaseHandler.hasComponent(c)) {
                    storeComponent(c, req, i);
                }
            }
            i++;
        }
    }

    private void storeComponent(Component comp, Requirements req, int s) {
        databaseHandler.insertComponent(comp, req, s);
        ClassList classList = serverAccessor.getClassList(comp.getCode());
        StatList statList = serverAccessor.getStatList(comp.getCode());
        if (classList != null) {
            for (ClassList.Entry entry : classList.getEntries()) {
                databaseHandler.insertClass(entry, comp.getCode());
            }
        }
        if (statList != null) {
            for (StatList.Entry entry : statList.getEntries()) {
                databaseHandler.insertStat(entry, comp.getCode());
            }
        }
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

    // TODO
    // Mudar isso de volta para pegar os dados do banco de dados.
    // Só comentei para poder testar.
    public Requirements getRequirements(String code) {
        Requirements req = databaseHandler.getRequirements(code);
        if (req.getSemesters().isEmpty()) {
            req = serverAccessor.getRequirements(code);
        }
        return req;
    }

    public ClassList getClassList(String code) {
        return databaseHandler.getClassList(code);
        //return serverAccessor.getClassList(code);
    }

    public StatList getStatList(String code) {
        return databaseHandler.getStatList(code);
        //return serverAccessor.getStatList(code);
    }

}
