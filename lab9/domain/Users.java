package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@Entity

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(length = 100)
    public String email;

    @Column(length = 100)
    public String password;

    @Column(length = 100)
    public String address;

    @Column
    public boolean isAdmin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    public Cart cart;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    public List<Orders> orders;

}
