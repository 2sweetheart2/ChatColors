package me.sweettie.ChatMaster;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonWorkd {
    Main plugin;
    File json;


    public JsonWorkd(Main plugin, File json) {
        this.plugin = plugin;
        this.json = json;
    }

    public void SaveColors(HashMap<String, String> hm) {
        Gson gson = new Gson();
        String json = gson.toJson(hm);
        OutputStream os;
        try {
            os = new FileOutputStream(this.json);
            os.flush();
            os.write(json.getBytes());
        } catch (Exception ignored) {

        }
    }

    public HashMap<String, String> getColors() throws FileNotFoundException {
        try {
            HashMap<String, String> homesList = new HashMap<>();
            JsonParser jsonParser = new JsonParser();
            Object parsed = jsonParser.parse(new FileReader(json.getPath()));
            JsonObject jsonObject = (JsonObject) parsed;
            Set<Map.Entry<String, JsonElement>> e = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> json : e) {
                homesList.put(json.getKey(), jsonObject.get(json.getKey()).getAsString());
            }
            return homesList;
        } catch (IOException e) {
            return null;
        }

    }

}
