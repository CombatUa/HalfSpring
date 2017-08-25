package ua.alex.ioc.entityJAXB;



import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beans")
public class BeanDefenitions {

    @XmlElement(name = "bean")
    private List<BeanDefenition> beanDefenitions;

    public List<BeanDefenition> getBeanDefenitions() {
        return beanDefenitions;
    }

    public void setBeanDefenitions(List<BeanDefenition> beanDefenitions) {
        this.beanDefenitions = beanDefenitions;
    }

    @Override
    public String toString() {
        return "BeanDefenitions{" +
                "beanDefenitions=" + beanDefenitions +
                '}';
    }
}
