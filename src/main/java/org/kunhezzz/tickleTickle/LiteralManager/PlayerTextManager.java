package org.kunhezzz.tickleTickle.LiteralManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTextManager {

    private final JavaPlugin plugin;
    private final Map<UUID, String> cache = new HashMap<>();

    public PlayerTextManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    //load

    public void load(Player player) {
        UUID uuid = player.getUniqueId();
        File file = getFile(uuid);

        if (!file.exists()) {
            return;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        cache.put(uuid, config.getString("text", ""));
    }

    // save

    public void save(Player player) {
        UUID uuid = player.getUniqueId();
        File file = getFile(uuid);

        try {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("text", cache.getOrDefault(uuid, ""));
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // getters and setters

    public void setText(Player player, String text) {
        cache.put(player.getUniqueId(), text);
    }

    public String getText(Player player) {
        return cache.getOrDefault(player.getUniqueId(), "");
    }

    // private tools

    private File getFile(UUID uuid) {
        File dir = new File(plugin.getDataFolder(), "playerdata");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, uuid + ".yml");
    }
}