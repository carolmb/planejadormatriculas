package planmat.internaldata;

import android.app.Activity;
import android.content.Context;

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
        return instance;
    }

    public void storeRequirements(Requirements req, Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        for(Semester s : req.getSemesters()) {
            for(Component c : s.getComponents()) {
                if (!databaseHandler.hasComponent(c)) {
                    storeComponent(c);
                }
            }
        }
    }

    private void storeComponent(Component comp) {
        databaseHandler.insertComponent(comp);
        ClassList classList = serverAccessor.getClassList(comp.getCode());
        StatList statList = serverAccessor.getStatList(comp.getCode());
        for(ClassList.Entry entry : classList.getEntries()) {
            databaseHandler.insertClass(entry);
        }
        for(StatList.Entry entry : statList.getEntries()) {
            databaseHandler.insertStat(entry);
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

    public Requirements getRequirements(String code) {
        return databaseHandler.getRequirements(code);
    }

    public ClassList getClassList(String code) {
        return databaseHandler.getClassList(code);
    }

    public StatList getStatList(String code) {
        return databaseHandler.getStatList(code);
    }

}
