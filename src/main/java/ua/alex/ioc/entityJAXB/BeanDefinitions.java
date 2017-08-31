package ua.alex.ioc.entityJAXB;



import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beans")
public class BeanDefinitions {

    @XmlElement(name = "bean")
    private List<BeanDefinition> beanDefinitions;

    public List<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public void setBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
    }

    @Override
    public String toString() {
        return "BeanDefinitions{" +
                "beanDefinitions=" + beanDefinitions +
                '}';
    }
}
