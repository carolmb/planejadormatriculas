package planmat.datarepresentation;

import java.util.ArrayList;

/**
 * Created by Luisa on 25/09/2016.
 */
public class StatList {

    public static class Entry {

        private int successes;
        private int fails;
        private int quits;

        public Entry(int successes, int fails, int quits) {
            this.successes = successes;
            this.fails = fails;
            this.quits = quits;
        }

        public int getSuccesses() {
            return successes;
        }

        public int getFails() {
            return fails;
        }

        public int getQuits() {
            return quits;
        }

    }


    private ArrayList<Entry> entries;

    public StatList() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
