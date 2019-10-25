package company;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = { "time", "status", "items", "user"}, name = "orders")
public class Orders {
    public String time;
    public String status;

    public Users getUser() {
        return user;
    }

    public Orders() {}

    public void setUser(Users user) {
        this.user = user;
    }
    @XmlElement
    public List<Item> items = new ArrayList<>();
    @XmlTransient
    public Users user;

    public void setItems(List<Item> items1) {
        for (int i = 0; i < items1.size(); i++) {
            items.add(items1.get(i));
        }
    }

    public Orders(String time, Users user) {
        this.time = time;
        setStatus("Accept");
        this.user = user;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void getItems() {
        for (int i = 0; i < this.items.size(); i++) {
            System.out.println("ORDER ~ " + time + ": " + this.items.get(i).name + " ~ $" + this.items.get(i).price);
        }
    }
}
