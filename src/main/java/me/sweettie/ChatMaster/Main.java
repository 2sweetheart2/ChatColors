package me.sweettie.ChatMaster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin implements Listener {

    public File json;
    public List<ChatColor> color;
    public List<String> color_name = new ArrayList<>();
    public HashMap<String, String> users = new HashMap<>();
    public StringJoiner string;
    public JsonWorkd jsonWorkd;
    Logger log = Bukkit.getLogger();

    @Override
    public void onEnable() {
        color = new ArrayList<>(16);
        {
            color.addAll(Arrays.asList(ChatColor.values()).subList(0, 16));
        }
        string = new StringJoiner("\n");
        for (ChatColor color : color) {
            string.add(color + color.name() + " | " + ChatColor.WHITE + color.name());
            color_name.add(color.name());
        }
        File folder = new File(getDataFolder() + "");
        if (!folder.exists()) {
            folder.mkdirs();
            try {
                folder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        json = new File(getDataFolder() + File.separator + "json.json");
        if (!json.exists()) {
            try {
                json.createNewFile();
            } catch (IOException e) {
                log.log(Level.WARNING, e.getMessage());
            }
        }
        jsonWorkd = new JsonWorkd(this, json);

        try {
            if (jsonWorkd.getColors() != null) users = jsonWorkd.getColors();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SetColor s = new SetColor(this);
        Objects.requireNonNull(getCommand("setcolor")).setExecutor(s);
        Objects.requireNonNull(getCommand("getcolors")).setExecutor(s);
        Bukkit.getPluginManager().registerEvents(this, this);
        List<Player> online = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : online) {
            if (users.containsKey(player.getUniqueId().toString())) {
                ChatColor c = color.get(color_name.indexOf(users.get(player.getUniqueId().toString())));
                player.setDisplayName(c + player.getName() + ChatColor.RESET);
                player.setPlayerListName(c + player.getName() + ChatColor.RESET);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void invite(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (users.containsKey(player.getUniqueId().toString())) {
            ChatColor c = color.get(color_name.indexOf(users.get(player.getUniqueId().toString())));
            player.setDisplayName(c + player.getName() + ChatColor.RESET);
            player.setPlayerListName(c + player.getName() + ChatColor.RESET);
        }
    }

    @EventHandler
    public void message(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getDisplayName() + ChatColor.RESET + ' ' + event.getMessage());
    }

}
