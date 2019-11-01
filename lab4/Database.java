package company;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {

    Auth auth = new Auth();
    Gson gson = new Gson();

    class Auth {
        String url = "jdbc:postgresql://localhost:4800/oodb";
        String user = "postgres";
        String password = "admin";
    }

    public int saveToDB(Shop shop) {
        log("Сохранение в БД");
        String SQL_SELECT1 = "insert into test (json) values ('" + gson.toJson(shop) + "') returning id";
        String SQL_SELECT2 = "insert into test (jsonb) values (cast(? as json)) returning id";
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
              PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT1)) {

            String object = gson.toJson(shop);

            long start;
            long finish;

            //preparedStatement.setString(1, object);

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time SaveToDB() - JSON:  " + (finish - start) + " нс.");
            while (resultSet.next()) {
                int i = resultSet.getInt("id");
                //return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT2)) {

            String object = gson.toJson(shop);

            long start;
            long finish;

            preparedStatement.setString(1, object);

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time SaveToDB() - JSONB:  " + (finish - start) + " нс.");
            while (resultSet.next()) {
                int i = resultSet.getInt("id");
                //return i;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void sortByASC() {
        List<String> json = new ArrayList<>();
        List<String> jsonb = new ArrayList<>();
        long start;
        long finish;
        log("Сортировка по ASC");
        String SQL_SELECT1 = "SELECT (json -> 'name') as json FROM test ORDER by jsonb ASC;";
        String SQL_SELECT2 = "SELECT (jsonb -> 'name') as jsonb FROM test ORDER by jsonb ASC;";
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time sortByASC() - JSON:  " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("json");
                json.add(example);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time sortByASC() - JSONB: " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
                jsonb.add(example);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("\nJSON:");
        for (int i = 0; i < json.size(); i++) {
            System.out.println(json.get(i));
        }
        System.out.println("\nJSONB:");
        for (int i = 0; i < jsonb.size(); i++) {
            System.out.println(jsonb.get(i));
        }
    }

    public void searchInDB() {
        long start;
        long finish;
        log("Поиск в БД");
        String SQL_SELECT1 = "SELECT json -> 'name' as json FROM test";
        String SQL_SELECT2 = "SELECT jsonb -> 'name' as jsonb FROM test";
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time searchInDB() - JSON:  " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("json");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time searchInDB() - JSONB: " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
            }
            System.out.println("Поле 'name' содержит: " + example);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadFromDB() {
        long start;
        long finish;
        log("Считывание из БД");
        String SQL_SELECT1 = "select json from test";
        String SQL_SELECT2 = "select jsonb from test";
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT1)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time loadFromDB() - JSON:  " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("json");
            }
            shop = gson.fromJson(example, Shop.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(auth.url, auth.user, auth.password);
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT2)) {

            String example = "";
            Shop shop = null;

            start = System.nanoTime();
            ResultSet resultSet = preparedStatement.executeQuery();
            finish = System.nanoTime();
            System.out.println("Time loadFromDB() - JSONB: " + (finish - start) + " нс.");

            while (resultSet.next()) {
                example = resultSet.getString("jsonb");
            }
            shop = gson.fromJson(example, Shop.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void log(String str) {
        System.out.println("\n[ " + new Date().toString() + " ] " + str + "\n");
    }
}
