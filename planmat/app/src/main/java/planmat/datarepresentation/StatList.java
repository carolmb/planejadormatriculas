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
        private int year;
        private int semester;

        public Entry(int successes, int fails, int quits, int year, int semester) {
            this.successes = successes;
            this.fails = fails;
            this.quits = quits;
            this.year = year;
            this.semester = semester;
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

        public int getYear() {
            return year;
        }

        public int getSemester() {
            return semester;
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

    public float getSuccessRate() {
        float mean = 0;
        for (StatList.Entry entry : entries) {
            float total = entry.getFails() + entry.getQuits() + entry.getSuccesses();
            mean += entry.getSuccesses() / total;
        }
        return mean / entries.size();
    }

}
