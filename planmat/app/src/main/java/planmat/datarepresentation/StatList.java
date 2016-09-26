package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 25/09/2016.
 */
public class StatList implements Serializable {

    public static class Entry implements Serializable {

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

        public String toString() {
            return "Aprovados: " + successes + "\nReprovados: " + quits + "\nTrancamentos: " + quits;
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
