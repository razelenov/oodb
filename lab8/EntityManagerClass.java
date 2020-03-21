import com.company.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityManagerClass implements EntityManager {

    HashMap<String, Boolean> typeSet = new HashMap<String, Boolean>();

    public EntityManagerClass() {
        typeSet.put("String", true);
        typeSet.put("Double", false);
        typeSet.put("Integer", false);
        typeSet.put("Long", false);
    }

    Connection connection = EntityManagerFactory.getConnection();

    @Override
    public void persist(Object var1) {
        String sql = "";
        List<String> values = new ArrayList<>();
        List<String> types = new ArrayList<>();

        int p = var1.getClass().getName().lastIndexOf('.');
        int l = var1.getClass().getName().length();
        String tableName = var1.getClass().getName().substring(p+1,l).toLowerCase();

        //System.out.println("Работаем с: " + tableName);
        sql = sql + "INSERT INTO " + tableName + " (";

        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class) || a.annotationType().equals(Id.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        //System.out.println("Сформировали метод: " + method.getName());

                        String value = null;
                        if (!typeSet.containsKey(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                            try {
                                method.invoke(var1, null);
                                sql = sql + field.getName() + ", ";
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                value = String.valueOf(method.invoke(var1, null));
                                sql = sql + field.getName() + ", ";
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        //System.out.println(value);
                        values.add(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1]);
                }  else if (a.annotationType().equals(ManyToOne.class)) {
                    sql = sql + field.getName() + "_id, ";
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1] + "_id");
                    System.out.println(types);
                    Method method = null;
                    try {
                        method = var1.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        Object ob = method.invoke(var1, null);
                        Method method2 = ob.getClass().getMethod(
                                "getId", null);
                        values.add(String.valueOf(method2.invoke(ob, null)));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        sql = sql.substring(0, sql.length() - 2) + ") values (";
        for (int i = 0; i < types.size(); i++) {
            try {
                if (typeSet.get(types.get(i))) {
                    sql = sql + "'" + values.get(i) + "', ";
                } else if (!typeSet.get(types.get(i))) {
                    sql = sql + values.get(i) + ", ";
                }
            } catch (Exception e) {
                sql = sql + values.get(i) + ", ";
            }
        }
        //System.out.println(types.toString());
        sql = sql.substring(0, sql.length() - 2) + ")";

        try {
            connection.prepareStatement(sql).execute();
            System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(sql);
    }

    @Override
    public <T> T merge(T var1) {
        String id = null;
        String sql = "";
        List<String> values = new ArrayList<>();
        List<String> types = new ArrayList<>();

        int p = var1.getClass().getName().lastIndexOf('.');
        int l = var1.getClass().getName().length();
        String tableName = var1.getClass().getName().substring(p+1,l).toLowerCase();

        //System.out.println("Работаем с: " + tableName);
        sql = sql + "UPDATE " + tableName + " SET ";
        try {
            Method method2 = var1.getClass().getMethod(
                    "getId", null);
            id = String.valueOf(method2.invoke(var1, null));
        } catch (Exception e) {}
        //values.add(String.valueOf(method2.invoke(ob, null)));
        Field[] fields = var1.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType().equals(Column.class) || a.annotationType().equals(Id.class)) {
                    try {
                        Method method = var1.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        //System.out.println("Сформировали метод: " + method.getName());

                        String value = null;
                        if (!typeSet.containsKey(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                            try {
                                method.invoke(var1, null);
                                sql = sql + field.getName() + "=" + value + ", ";
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                value = String.valueOf(method.invoke(var1, null));
                                if (typeSet.get(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1])) {
                                    sql = sql + field.getName() + "='" + value + "', ";
                                } else {
                                    sql = sql + field.getName() + "=" + value + ", ";
                                }
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        //System.out.println(value);
                        values.add(value);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1]);
                }  else if (a.annotationType().equals(ManyToOne.class)) {
                    //sql = sql + field.getName() + "_id, ";
                    types.add(field.getType().toString().split("\\.")[field.getType().toString().split("\\.").length - 1] + "_id");
                    System.out.println(types);
                    Method method = null;
                    try {
                        method = var1.getClass().getMethod(
                                "get" + field.getName().substring(0, 1).toUpperCase() +
                                        field.getName().substring(1), null);
                        Object ob = method.invoke(var1, null);
                        Method method2 = ob.getClass().getMethod(
                                "getId", null);
                        values.add(String.valueOf(method2.invoke(ob, null)));
                        sql = sql + field.getName() + "_id =" + String.valueOf(method2.invoke(ob, null) + ", ");
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        sql = sql.substring(0, sql.length() -2) + " WHERE id=" + id;

        try {
            connection.prepareStatement(sql).execute();
            System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
        return null;
    }

    @Override
    public void remove(Object var1) {
        String id = null;
        int p = var1.getClass().getName().lastIndexOf('.');
        int l = var1.getClass().getName().length();
        String tableName = var1.getClass().getName().substring(p+1,l).toLowerCase();
        try {
            Method method2 = var1.getClass().getMethod(
                    "getId", null);
            id = String.valueOf(method2.invoke(var1, null));
        } catch (Exception e) {}
        String sql = "DELETE FROM " + tableName + " WHERE id=" +id;
        try {
            connection.prepareStatement(sql).execute();
            System.out.println(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
    }

    @Override
    public <T> T find(Class<T> var1, Object var2) {
        String id = null;
        Object objectOfClass = null;

        try {
            Class<?> clazz = Class.forName(String.valueOf(var1).split(" ")[1].trim());
            Constructor con = clazz.getConstructor();
            objectOfClass = con.newInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //int p = var1.getClass().getName().lastIndexOf('.');
        //int l = var1.getClass().getName().length();
        String tableName = var1.getSimpleName().toLowerCase();
        /*try {
            Method method2 = var1.getClass().getMethod(
                    "getId", null);
            id = String.valueOf(method2.invoke(var1, null));
        } catch (Exception e) {}*/
        String sql = "SELECT * FROM " + tableName + " WHERE id=" +var2;
        try {
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int j = 0; j < resultSetMetaData.getColumnCount(); j++) {
                    String columnName = resultSetMetaData.getColumnName(j + 1);
                    if (columnName.contains("_id")) {
                        String[] line = columnName.split("_");
                        String fieldNameForMethod = line[0].substring(0, 1).toUpperCase() +
                                line[0].substring(1);
                        Object columnValue = resultSet.getLong(columnName);
                        System.out.println(columnValue);
                    } else {
                        Object columnValue = resultSet.getObject(columnName.toLowerCase());
                        //System.out.println(columnValue);
                        String fieldNameForMethod = columnName.substring(0, 1).toUpperCase() +
                                columnName.substring(1);
                        Method method = null;
                        try {
                            if (columnValue.getClass() == String.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, String.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == Double.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Double.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == Long.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Long.class);
                                method.invoke(objectOfClass, columnValue);
                            } else if (columnValue.getClass() == int.class) {
                                method = objectOfClass.getClass().getMethod(
                                        "set" + fieldNameForMethod, Integer.class);
                                method.invoke(objectOfClass, columnValue);
                            }
                        } catch (Exception e) {}

                    }
                    //System.out.println(columnName);
                    //Object columnValue = resultSet.getObject(col.toLowerCase());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
        return (T) objectOfClass;
    }

    @Override
    public void refresh(Object var1) {
        String id = null;
        try {
            Method method2 = var1.getClass().getMethod(
                    "getId", null);
            id = String.valueOf(method2.invoke(var1, null));
        } catch (Exception e) {}
        var1 = find(var1.getClass(), id);
    }

}
