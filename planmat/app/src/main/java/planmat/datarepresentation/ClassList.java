package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Luisa on 25/09/2016.
 */
public class ClassList implements Serializable {

    public static class Entry implements Serializable {
        private int id;
        private String code;
        private String semester;
        private ArrayList<String> professors;
        private String hour;

        private int successes;
        private int fails;
        private int quits;

        public Entry(int id, String code, String semester, ArrayList<String> professors, String hour,
                     int successes, int fails, int quits) {
            this.id = id;
            this.code = code;
            this.semester = semester;
            this.professors = professors;
            this.hour = hour;
            this.successes = successes;
            this.fails = fails;
            this.quits = quits;
        }

        public int getID() {
            return id;
        }

        public String getSemester() {
            return semester;
        }

        public ArrayList<String> getProfessors() {
            return professors;
        }

        public String getHour() {
            return hour;
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
            return semester + " - " + code;
        }
    }

    private ArrayList<Entry> entries;

    public ClassList() {
        this.entries = new ArrayList<>();
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

}
