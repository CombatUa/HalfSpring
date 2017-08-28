package ua.alex.ioc.entity;


public class Bean {
    private String beadId;
    private Object value;

    @Override
    public String toString() {
        return "Bean{" +
                "beadId='" + beadId + '\'' +
                ", value=" + value +
                '}';
    }

    public String getBeadId() {
        return beadId;
    }

    public void setBeadId(String beadId) {
        this.beadId = beadId;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
