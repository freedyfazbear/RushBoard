package me.ishift.rushboard.util;

import me.ishift.rushboard.RushBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardBuilder {
    private Player player;
    private String title;
    private List<String> entries;

    public ScoreboardBuilder(Player player) {
        this.player = player;
        this.entries = new ArrayList<>();
    }

    public ScoreboardBuilder setTitle(String title) {
        if (RushBoard.isPlacehoderApiHook()) {
            this.title = PlaceholderUtil.replace(this.player, title);
        } else {
            this.title = title;
        }
        return this;
    }

    public ScoreboardBuilder setEntries(List<String> entries) {
        this.entries.addAll(entries);
        return this;
    }

    public ScoreboardBuilder addEntry(String entry) {
        entries.add(entry);
        return this;
    }

    public Scoreboard build() {
        final ScoreboardManager manager = Bukkit.getScoreboardManager();
        final Scoreboard board = manager.getNewScoreboard();
        Objective objective;

        if (RushBoard.isOldVersion()) {
            objective = board.registerNewObjective("rushboard", "dummy");
        } else {
            objective = board.registerNewObjective("rushboard", "dummy", "rushboard");
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int line = 0;

        for (String entry : this.entries) {
            Score score;
            if (RushBoard.isPlacehoderApiHook()) {
                score = objective.getScore(ChatUtil.fixColor(PlaceholderUtil.replace(this.player, entry)));
                objective.setDisplayName(ChatUtil.fixColor(PlaceholderUtil.replace(this.player, this.title)));
            } else {
                score = objective.getScore(entry);
                objective.setDisplayName(ChatUtil.fixColor(this.title));
            }
            score.setScore(line);
            line++;
        }

        return board;
    }
}
