package by.vsu.kovzov.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ReceivedMark implements Serializable {
    private Long id;

    private Mark mark;

    private Date date;

    private Discipline discipline;

    private int semester;

    public ReceivedMark() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceivedMark mark1 = (ReceivedMark) o;
        return semester == mark1.semester && Objects.equals(id, mark1.id) && Objects.equals(mark, mark1.mark) && Objects.equals(date, mark1.date) && Objects.equals(discipline, mark1.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, date, discipline, semester);
    }

    @Override
    public String toString() {
        return "ReceivedMark{" +
                "id=" + id +
                ", mark=" + mark +
                ", date=" + date +
                ", discipline=" + discipline +
                ", semester=" + semester +
                '}';
    }
}
