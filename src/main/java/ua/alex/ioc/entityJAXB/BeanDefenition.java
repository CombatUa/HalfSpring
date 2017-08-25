package ua.alex.ioc.entityJAXB;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bean")
public class BeanDefenition {

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "class")
    private String clazz;

    @XmlElement(name = "property")
    private List<Property> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }


    @Override
    public String toString() {
        return "BeanDefenition{" +
                "id='" + id + '\'' +
                ", clazz='" + clazz + '\'' +
                ", properties=" + properties +
                '}';
    }
}
