package planmat.appcore;

/**
 * Created by User on 13/11/2016.
 */
public class CheckSemesterFactory {

    private static CheckSemesterFactory instance;

    public static int BYSUCCESSES = 1;
    public static int BYWORKLOAD = 2;

    private CheckSemesterFactory() {}

    public static CheckSemesterFactory getInstance() {
        if(instance == null) {
            instance = new CheckSemesterFactory();
        }
        return instance;
    }

    public CheckSemester getCheckSemester(int type) {
        CheckSemester checkSemester = null;
        if(type == BYSUCCESSES) {
            checkSemester = new CheckSemesterBySuccesses();
        } else if(type == BYWORKLOAD) {
            // TODO: por carga horária
        }
        return checkSemester;
    }
}
