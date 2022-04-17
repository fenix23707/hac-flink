package by.vsu.kovzov.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ReceivedMark implements Serializable {
    private Long id;

    private Mark mark;

    private Date date;

    public ReceivedMark() {
        this.mark = new Mark();
    }

    public ReceivedMark(Mark mark, Date date) {
        this.mark = mark;
        this.date = date;
    }

    public ReceivedMark(Long id, Mark mark, Date date) {
        this.id = id;
        this.mark = mark;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceivedMark that = (ReceivedMark) o;
        return Objects.equals(id, that.id) && Objects.equals(mark, that.mark) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mark, date);
    }

    @Override
    public String toString() {
        return "ReceivedMark{" +
                "id=" + id +
                ", mark=" + mark +
                ", date=" + date +
                '}';
    }
}
