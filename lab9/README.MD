```html
Hibernate: create table Cart (id int8 not null, primary key (id))
Hibernate: create table cart_item (cart_id int8 not null, item_id int8 not null)
Hibernate: create table Categories (id int8 not null, name varchar(20), primary key (id))
Hibernate: create table Item (id int8 not null, info varchar(255), name varchar(255), price float8, category_id int8, primary key (id))
Hibernate: create table Orders (id int8 not null, ordertime timestamp, status_id int8, users_id int8, primary key (id))
Hibernate: create table orders_items (orders_id int8 not null, items_id int8 not null)
Hibernate: create table Status (id int8 not null, name varchar(255), primary key (id))
Hibernate: create table Users (id int8 not null, address varchar(100), email varchar(100), isAdmin boolean, password varchar(100), cart_id int8, primary key (id))
Hibernate: create sequence hibernate_sequence start 1 increment 1
Hibernate: alter table if exists cart_item add constraint FKgsrwpmubhdb72ep6a74fsmtch foreign key (item_id) references Item
Hibernate: alter table if exists cart_item add constraint FKm3v92110i8kdib56gun5n5wh6 foreign key (cart_id) references Cart
Hibernate: alter table if exists Item add constraint FKpcwve2md6dj743uit28sv7ulj foreign key (category_id) references Categories
Hibernate: alter table if exists Orders add constraint FKg09a38ionvsxoej9hy9kyv36a foreign key (status_id) references Status
Hibernate: alter table if exists Orders add constraint FK2osxvn2qb2yp7eppfohh92ikd foreign key (users_id) references Users
Hibernate: alter table if exists orders_items add constraint FKo80sr3q05en292mucifmqe4wo foreign key (items_id) references Item
Hibernate: alter table if exists orders_items add constraint FK7f9m1ckrxa2bye36h4v5bcm0c foreign key (orders_id) references Orders
Hibernate: alter table if exists Users add constraint FKfwhkkdsc0grj2fhv59bv3ch74 foreign key (cart_id) references Cart
```

```html
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence
            http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd" version="2.2">
    <persistence-unit name="lab9">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <properties>
            <property name="javax.persistence.jdbc.driver" 				value="org.postgresql.Driver"/>
            <property name="javax.persistence.jdbc.url" 	value="jdbc:postgresql://127.0.0.1:4800/lab9"/>
            <property name="javax.persistence.jdbc.user" value="postgres"/>
            <property name="javax.persistence.jdbc.password" value="admin"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/> <!-- none, 	create  -->
        </properties>
    </persistence-unit>
</persistence>
```

![Иллюстрация к проекту](https://i.imgur.com/jDXlKkE.png)
