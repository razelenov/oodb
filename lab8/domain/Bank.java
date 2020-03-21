package domain;

import com.company.annotation.Column;
import com.company.annotation.Entity;
import com.company.annotation.Id;

@Entity
public class Bank {

    @Id
    public Long id;

    @Column
    public String name;

    @Column
    public String owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}