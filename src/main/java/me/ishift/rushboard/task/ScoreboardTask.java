package me.ishift.rushboard.task;

import me.ishift.rushboard.RushBoard;
import me.ishift.rushboard.util.ScoreboardBuilder;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardTask implements Runnable {
    @Override
    public void run() {
        // Checking if scoreboard is enabled.
        if (!RushBoard.isBoardEnabled()) {
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            // Checking if player has toggled of scoreboard.
            if (RushBoard.getToggledOff().contains(player.getName())) {
                return;
            }

            // Checking player's world.
            if (!RushBoard.getInstance().getConfig().getStringList("worlds").contains(player.getWorld().getName())) {
                return;
            }

            final Scoreboard board = new ScoreboardBuilder(player)
                    .setTitle(RushBoard.getString("scoreboard.title"))
                    .setEntries(RushBoard.getInstance().getConfig().getStringList("scoreboard.contents"))
                    .build();
            player.setScoreboard(board);
        });
    }
}
