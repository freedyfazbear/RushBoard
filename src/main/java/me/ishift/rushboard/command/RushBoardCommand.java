package me.ishift.rushboard.command;

import me.ishift.rushboard.RushBoard;
import me.ishift.rushboard.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RushBoardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length < 1) {
            RushBoard.getInstance().getConfig().getStringList("messages.main-command").forEach(string -> sender.sendMessage(ChatUtil.fixColor(string.replace("%alias%", s))));
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("rushboard.reload")) {
                sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.no-permission")));
                return true;
            }
            RushBoard.getInstance().reloadConfig();
            sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.reload")));
            return true;
        }
        if (args[0].equalsIgnoreCase("toggle") && sender instanceof Player) {
            if (RushBoard.getToggledOff().contains(sender.getName())) {
                RushBoard.removeToggledOff(sender.getName());
                sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.toggled-on")));
                return true;
            }
            RushBoard.addToggledOff(sender.getName());
            sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.toggled-off")));
            ((Player) sender).setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            return true;
        }
        else {
            sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.unknown-command")));
        }
        return true;
    }
}
