package me.ishift.rushboard;

import me.ishift.rushboard.task.ScoreboardTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RushBoard extends JavaPlugin {
    private static String nmsVersion;
    private static boolean placehoderApiHook;
    private static List<String> toggledOff = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

        placehoderApiHook = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        if (placehoderApiHook) {
            new RushBoardPlaceholder(this).register();
        }
        this.loadData();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ScoreboardTask(), 0L, this.getConfig().getLong("interval"));
    }

    @Override
    public void onDisable() {
        this.saveData();
    }

    private void saveData() {
        final File file = new File(this.getDataFolder(), "data.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (!toggledOff.isEmpty()) {
            config.set("toggled-off", toggledOff);
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        final File file = new File(this.getDataFolder(), "data.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("toggled-off", Collections.singletonList("example__"));
        }
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        toggledOff = config.getStringList("toggled-off");
    }

    public static RushBoard getInstance() {
        return JavaPlugin.getPlugin(RushBoard.class);
    }

    public static String getString(String path) {
        return getInstance().getConfig().getString(path);
    }

    public static boolean isOldVersion() {
        return !nmsVersion.startsWith("v1_13") && !nmsVersion.startsWith("v1_14") && !nmsVersion.startsWith("v1_15") && !nmsVersion.startsWith("v1_16");
    }

    public static boolean isPlacehoderApiHook() {
        return placehoderApiHook;
    }
}
