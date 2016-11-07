package planmat.internaldata;

/**
 * Created by Luisa on 24/10/2016.
 */
public class MyUserPrefs extends UserPrefs {

    private String data;

    public MyUserPrefs(String name, String data) {
        super(name);
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
