package planmat.datarepresentation;

import java.util.ArrayList;

/**
 * Created by Luisa on 19/09/2016.
 */
public class RequirementsList {

    public static class Entry {

        private int id;
        private String name;

        public Entry(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getID() {
            return id;
        }

        public String getName() {
            return name;
        }

    }

    private ArrayList<Entry> entries;

    public RequirementsList() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
