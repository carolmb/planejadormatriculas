package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 17/09/2016.
 */
public class User implements Serializable {

    public static class Entry {
        public int id;
    }

    private int id;
    private String name;
    private ArrayList<Entry> entries;

    public User(int id, String name, ArrayList<Entry> entries) {
        this.id = id;
        this.name = name;
        this.entries = entries;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
