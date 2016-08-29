package datarepresentation;

import java.util.ArrayList;

/**
 * Created by User on 24/08/2016.
 * Representa um aluno.
 */
public class Student {
    private String student;
    private Requirements requirements;
    private ArrayList<ComponentClass> coursedComponents;

    public Student(String student, Requirements requirements, ArrayList<ComponentClass> coursedComponents) {
        this.student = student;
        this.requirements = requirements;
        this.coursedComponents = coursedComponents;
    }
}
