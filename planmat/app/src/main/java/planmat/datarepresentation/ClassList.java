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
        private ArrayList<String> professors;
        private String hour;
        private int year;
        private int semester;

        public Entry(int id, String code, ArrayList<String> professors,
                     String hour, int y, int s) {
            this.id = id;
            this.code = code;
            this.year = y;
            this.semester = s;
            this.professors = professors;
            this.hour = hour;
        }

        public int getID() {
            return id;
        }

        public int getYear() {
            return year;
        }

        public int getSemester() {
            return semester;
        }

        public String getSemesterString() {
            return year + "." + semester;
        }

        public ArrayList<String> getProfessors() {
            return professors;
        }

        public String getHour() {
            return hour;
        }

        public String toString() {
            return getSemesterString() + " - " + code;
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
