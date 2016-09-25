package planmat.datarepresentation;

import java.io.Serializable;
import java.util.ArrayList;

import planmat.internaldata.UserPrefs;

/**
 * Created by Luisa on 25/09/2016.
 */
public class ClassList implements Serializable {

    public class Entry implements Serializable {
        private int id;
        private String semester;
        private String professor;
        private String hour;

        public Entry(int id, String semester, String professor, String hour) {
            this.id = id;
            this.semester = semester;
            this.professor = professor;
            this.hour = hour;
        }

        public int getID() {
            return id;
        }

        public String getSemester() {
            return semester;
        }

        public String getProfessor() {
            return professor;
        }

        public String getHour() {
            return hour;
        }

        public String toString() {
            return semester + " - " + id;
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
