package by.vsu.kovzov.model;

import java.util.Objects;

public class Mark {
    private Integer id;

    private int value;

    private String valueStr;

    private int markScale;

    public Mark() {
    }

    public Mark(int value, String valueStr, int markScale) {
        this.value = value;
        this.valueStr = valueStr;
        this.markScale = markScale;
    }

    public Mark(Integer id, int value, String valueStr, int markScale) {
        this.id = id;
        this.value = value;
        this.valueStr = valueStr;
        this.markScale = markScale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValueStr() {
        return valueStr;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }

    public int getMarkScale() {
        return markScale;
    }

    public void setMarkScale(int markScale) {
        this.markScale = markScale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return value == mark.value && markScale == mark.markScale && Objects.equals(id, mark.id) && Objects.equals(valueStr, mark.valueStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, valueStr, markScale);
    }

    @Override
    public String toString() {
        return valueStr;
    }
}
