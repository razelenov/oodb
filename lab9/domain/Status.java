package domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Status extends Directory {

    public Status() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column
    public String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    public List<Orders> orders;
}
