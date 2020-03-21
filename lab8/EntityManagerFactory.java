import com.company.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class EntityManagerFactory {

    public static HashMap<String, HashSet<String>> tables = null;
    private static Connection connection;

    public static boolean isDatabaseFull() {
        boolean result = true;
        HashMap<String, HashSet<String>> tables = new HashMap<>();

        try (Connection connection = getConnection()) {

            //System.out.println("Список таблиц:");
            List<String> tbls = getTables(connection);
            //tbls.forEach(System.out::println);

            for (String table : tbls) {
                //System.out.println("Список полей таблицы "+table+":");
                List<String> fields = getFields(connection, table);

                HashSet<String> hashSetFields = new HashSet<>();
                fields.forEach(f -> {
                    //System.out.println(f);
                    hashSetFields.add(f);
                });

                tables.put(table, hashSetFields);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        EntityManagerFactory.tables = tables;

        List<Class<?>> classList = PathScan.find("domain");
        List<Class> entities = new ArrayList<>();
        for (int i = 0; i < classList.size(); i++) {
            Annotation[] annotations = classList.get(i).getAnnotations();
            if (annotations != null) {
                for (Annotation a : annotations) {
                    if (a.annotationType().equals(Entity.class)) {
                        entities.add(classList.get(i));
                        System.out.println(classList.get(i));
                        //System.out.println("\t" + classList.get(i).getName() + " is entity!");
                    }
                }
            }
        }

        System.out.println("Сущности:");
        for (int i = 0; i < entities.size(); i++) {
            if (tables.containsKey(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1])) {
                System.out.println("[+] " + entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1] + " найден в БД!");
            } else {
                System.out.println("[!] " + entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1] + "не найден!");
                result = false;
            }
        }

        List<String> manyToOne = new ArrayList<>(); // + _id
        List<String> oneToOne = new ArrayList<>(); // + _id
        List<String> columns = new ArrayList<>(); // Nothing
        List<String> oneToMany = new ArrayList<>(); // (1)_(2)
        List<String> manyToMany = new ArrayList<>(); // (1)_(2)
        List<String> id = new ArrayList<>(); // id
        for (int i = 0; i < entities.size(); i++) {
            System.out.println("\nСущность: " + entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1]);
            Field[] fields = entities.get(i).getDeclaredFields();
            for (Field f : fields) {
                Annotation[] fannotations = f.getAnnotations();
                for (Annotation a : fannotations) {
                    //System.out.println(a.annotationType());
                    if (a.annotationType().equals(ManyToOne.class)) {
                        //System.out.println(f.getName());
                        manyToOne.add(f.getName().toLowerCase());
                    } else if (a.annotationType().equals(OneToOne.class)) {
                        //System.out.println(f.getName());
                        oneToOne.add(f.getName().toLowerCase());
                    } else if (a.annotationType().equals(OneToMany.class)) {
                        oneToMany.add(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1] + "_" + f.getName().substring(0, f.getName().length() - 1));
                    } else if (a.annotationType().equals(ManyToMany.class)) {
                        manyToMany.add(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1] + "_" + f.getName().substring(0, f.getName().length() - 1));
                    } else if (a.annotationType().equals(Column.class)) {
                        columns.add(f.getName().toLowerCase());
                    } else if (a.annotationType().equals(Id.class)) {
                        id.add(f.getName().toLowerCase());
                    }
                }
            }
            List<String> remove = new ArrayList<>();
            for (String s : columns) {
                if (manyToOne.contains(s) || oneToOne.contains(s)) {
                    remove.add(s);
                }
            }
            for (String s : manyToMany) {
                remove.add(s.split("_")[1] + "s");
            }
            for (String s : oneToMany) {
                remove.add(s.split("_")[1] + "s");
            }
            for (String s : remove) {
                columns.remove(s);
            }
            /*System.out.println("COLUMNS: " + columns.toString());
            System.out.println("ID: " + id.toString());
            System.out.println("ManyToOne: " + manyToOne.toString());
            System.out.println("ManyToMany: " + manyToMany.toString());
            System.out.println("OneToMany: " + oneToMany.toString());
            System.out.println("OneToOne: " + oneToOne.toString());*/

            System.out.println("---- Поиск полей ----");
            for (String s : id) {
                System.out.println(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1]);
                if ((tables.get(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1])).contains(s)) {
                    System.out.println("[+] " + s);
                } else {
                    System.out.println("[!] " + s);
                    result = false;
                }
            }
            for (String s : columns) {
                if ((tables.get(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1])).contains(s)) {
                    System.out.println("[+] " + s);
                } else {
                    System.out.println("[!] " + s);
                    result = false;
                }
            }
            for (String s : oneToOne) {
                if ((tables.get(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1])).contains(s + "_id")) {
                    System.out.println("[+] " + s + "_id");
                } else {
                    System.out.println("[!] " + s + "_id");
                    result = false;
                }
            }
            for (String s : manyToOne) {
                if ((tables.get(entities.get(i).getCanonicalName().toLowerCase().split("\\.")[1])).contains(s + "_id")) {
                    System.out.println("[+] " + s + "_id");
                } else {
                    System.out.println("[!] " + s + "_id");
                    result = false;
                }
            }
            if (manyToMany.size() != 0 || oneToMany.size() != 0) {
                System.out.println("---- Поиск таблиц ----");
                for (String s : oneToMany) {
                    if (tables.containsKey(s)) {
                        System.out.println("[+] " + s);
                    } else {
                        System.out.println("[!] " + s);
                        result = false;
                    }
                }
                for (String s : manyToMany) {
                    if (tables.containsKey(s)) {
                        System.out.println("[+] " + s);
                    } else {
                        System.out.println("[!] " + s);
                        result = false;
                    }
                }
            }

            columns = new ArrayList<>();
            id = new ArrayList<>();
            manyToMany = new ArrayList<>();
            manyToOne = new ArrayList<>();
            oneToMany = new ArrayList<>();
            oneToOne = new ArrayList<>();
        }
        return result;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("org.postgresql.Driver");
            String dbURL = "jdbc:postgresql://localhost:4800/manager";
            connection = DriverManager.getConnection(dbURL, "postgres", "admin");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static List<String> getTables(Connection connection) throws SQLException {

        List<String> lst = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_type = 'BASE TABLE' AND" +
                        " table_schema NOT IN ('pg_catalog', 'information_schema')");

        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            String s = resultSet.getString("table_name");
            lst.add(s);
        }

        st.close();
        return lst;
    }

    public static List<String> getFields(Connection connection, String tableName) throws SQLException {

        List<String> lst = new ArrayList<>();

        PreparedStatement st = connection.prepareStatement(
                "SELECT a.attname " +
                        "FROM pg_catalog.pg_attribute a " +
                        "WHERE a.attrelid = (SELECT c.oid FROM pg_catalog.pg_class c " +
                        "LEFT JOIN pg_catalog.pg_namespace n ON n.oid = c.relnamespace " +
                        " WHERE pg_catalog.pg_table_is_visible(c.oid) AND c.relname = ? )" +
                        " AND a.attnum > 0 AND NOT a.attisdropped");

        st.setString(1, tableName);
        ResultSet resultSet = st.executeQuery();

        while (resultSet.next()) {
            String s = resultSet.getString("attname");
            lst.add(s);
        }

        st.close();
        return lst;
    }

}