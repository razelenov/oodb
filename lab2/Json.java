package com.company;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;

public class Json {
    Gson gson = new Gson();
    public void saveJSON(Object object) {
        try (Writer writer = new FileWriter("database.json")) {
            String json = gson.toJson(object);
            writer.write(json);
            System.out.println("Сохранено: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shop loadJSON() throws IOException {
        File file = new File("database.json");
        String str = "";
        if (file.exists()) {
            str = new String(Files.readAllBytes(file.toPath()));
        }
        return gson.fromJson(str, Shop.class);
    }
}
