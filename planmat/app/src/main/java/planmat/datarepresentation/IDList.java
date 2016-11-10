package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 19/09/2016.
 */
public class IDList implements Serializable {

    public static class Entry {

        private String id;
        private String name;

        public Entry(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getID() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }

    }

    private ArrayList<Entry> entries;

    public IDList() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
