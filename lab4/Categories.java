package company;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name"}, name = "categories")
public class Categories {

    public Categories() {}

    public String name;

    public Categories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
