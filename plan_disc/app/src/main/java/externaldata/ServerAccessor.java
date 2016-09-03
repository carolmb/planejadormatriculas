package externaldata;

/**
 * Created by Ana Caroline on 03/09/2016.
 */
public abstract class ServerAccessor {
    abstract void getRequirements(final ActionRequest finalAction, final String nameMajor, final int year, final int semester);
    abstract void getStudent(String idStudent);
    abstract void getComponent(String code);
    abstract void getComponentClass();
}
