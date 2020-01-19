package me.ishift.rushboard;

import me.ishift.rushboard.task.ScoreboardTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RushBoard extends JavaPlugin {
    private static String nmsVersion;
    private static boolean placehoderApiHook;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        nmsVersion = Bukkit.getServer().getClass().getPackage().getName();
        nmsVersion = nmsVersion.substring(nmsVersion.lastIndexOf(".") + 1);

        placehoderApiHook = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new ScoreboardTask(), 0L, this.getConfig().getLong("interval"));
    }

    @Override
    public void onDisable() {
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
