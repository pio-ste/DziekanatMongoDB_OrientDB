package OrientDB.dto;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.util.UUID;

public class Student {


    private UUID id;
    private String firstName, lastName, specialization, group;
    private int studyYear;

    private Oceny oceny = new Oceny();

    public Student() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public Oceny getOceny() {
        return oceny;
    }

    public void setOceny(Oceny oceny) {
        this.oceny = oceny;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + getId() + '\'' +
                ", Imię='" + getFirstName() + '\'' +
                ", Nazwisko='" + getLastName() + '\'' +
                ", kierunek='" + getSpecialization() + '\'' +
                ", grupa='" + getGroup() + '\'' +
                ", rok studiów=" + getStudyYear() +
                ", " + getOceny().toString() +
                ']';
    }
}
