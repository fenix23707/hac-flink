package by.vsu.kovzov.model;

import java.util.Objects;

public class Discipline {
    private Integer id;

    private String shortname;

    private String fullname;

    public Discipline() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discipline that = (Discipline) o;
        return Objects.equals(id, that.id) && Objects.equals(shortname, that.shortname) && Objects.equals(fullname, that.fullname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortname, fullname);
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "id=" + id +
                ", shortname='" + shortname + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
