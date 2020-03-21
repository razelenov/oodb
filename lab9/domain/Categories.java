package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Entity
public class Categories extends Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(length = 20)
    public String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Item> items;
}
