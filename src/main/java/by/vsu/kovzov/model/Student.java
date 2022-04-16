package by.vsu.kovzov.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private Long id;

    private String surname;

    private String name;

    private String patronymic;

    private List<ReceivedMark> receivedMarks;

    public Student() {
        receivedMarks = new ArrayList<>();
    }

    public Student(String surname, String name, String patronymic, List<ReceivedMark> receivedMarks) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.receivedMarks = receivedMarks;
    }

    public Student(Long id, String surname, String name, String patronymic, List<ReceivedMark> receivedMarks) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.receivedMarks = receivedMarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<ReceivedMark> getReceivedMarks() {
        return receivedMarks;
    }

    public void setReceivedMarks(List<ReceivedMark> receivedMarks) {
        this.receivedMarks = receivedMarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(surname, student.surname) && Objects.equals(name, student.name) && Objects.equals(patronymic, student.patronymic) && Objects.equals(receivedMarks, student.receivedMarks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, receivedMarks);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", receivedMarks=" + receivedMarks +
                '}';
    }
}
