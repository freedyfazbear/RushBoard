package me.ishift.rushboard.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderUtil {
    public static String replace(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
