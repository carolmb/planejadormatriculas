package planmat.datarepresentation;

/**
 * Created by User on 03/09/2016.
 */
public class ComponentClass {
    private Component component;
    private String semester;
    private String professor;
    private String hour;

    public ComponentClass(Component component, String semester, String professor, String hour) {
        this.semester = semester;
        this.professor = professor;
        this.hour = hour;
    }

    public Component getComponent() {
        return component;
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
}
