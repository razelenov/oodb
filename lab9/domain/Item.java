package domain;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    public Categories category;

    @Column
    public Double price;

    @Column
    public String info;

    @ManyToMany(mappedBy = "items")
    public List<Orders> orders;

    @ManyToMany(mappedBy = "items")
    public List<Cart> carts;
}
