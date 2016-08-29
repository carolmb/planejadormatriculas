package datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2016.
 * Representa uma turma do SIGAA.
 */
public class ComponentClass extends Component {
    private String semester;
    private String professor;
    private String hour;

    public ComponentClass(String name,
                          String code,
                          ArrayList<Component> prerequisite,
                          ArrayList<Component> corequisite,
                          String semester,
                          String professor,
                          String hour) {
        super(name, code, prerequisite, corequisite);
        this.semester = semester;
        this.professor = professor;
        this.hour = hour;
    }
}
