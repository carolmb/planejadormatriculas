package planmat.datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 25/09/2016.
 */
public class StatisticsClass {
    public static class Component {
        private String name;
        private String code;
        private int year;
        private int semester;
        private int passed;
        private int failed;

        public Component(String name, String code, int year, int semester, int passed, int failed) {
            this.name = name;
            this.code = code;
            this.year = year;
            this.semester = semester;
            this.passed = passed;
            this.failed = failed;
        }

        @Override
        public String toString() {
            return "Semestre: " + year + "." + semester + "\nAprovados: " + passed + "\nReprovados: " + failed;
        }
    }
    ArrayList<Component> statistics;

    public StatisticsClass() {
        statistics = new ArrayList<Component>();
    }

    public ArrayList<Component> getStatistics() {
        return statistics;
    }

    public float averagePassed() {
        float average = 0;
        for (Component c: statistics) {
            average+=c.passed;
        }
        return average/statistics.size();
    }

    public float averageFailed() {
        float average = 0;
        for (Component c: statistics) {
            average+=c.failed;
        }
        return average/statistics.size();
    }
}
