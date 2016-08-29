package externaldata;

import datarepresentation.Component;
import datarepresentation.ComponentClass;
import datarepresentation.Requirements;
import datarepresentation.Student;

/**
 * Created by User on 24/08/2016.
 */
public class SIGAAServerAccesor implements ServerAccessor {

    // GET /consulta/curso/componentes/{idCurriculo}
    public Requirements getRequirements(String idRequirements) {
        return null;
    }

    // GET /consulta/matriculacomponente/discente/{idDiscente}/all
    public Student getStudent(String idStudent) {
        return null;
    }

    public Component getComponent(String code) {
        return null;
    }

    public ComponentClass getComponentClass() {
        return null;
    }
}
