package company;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name", "id_category", "price", "info"}, name = "item")
public class Item {
    public String name;
    @XmlTransient
    public Categories id_category1;
    public Double price;
    public String info;

    public Item(String name, Double price, String info) {
        this.name = name;
        this.price = price;
        this.info = info;
    }

    public Item() {}

    public void setId_category(Categories id_category) {
        this.id_category1 = id_category;
    }

    public String getName() {
        return name;
    }
    public Categories getId_category() {
        return id_category1;
    }

    public Double getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }
}
