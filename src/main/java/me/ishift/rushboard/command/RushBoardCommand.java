package me.ishift.rushboard.command;

import me.ishift.rushboard.RushBoard;
import me.ishift.rushboard.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
        if (args[0].equalsIgnoreCase("toggle")) {
            return true;
        }
        else {
            sender.sendMessage(ChatUtil.fixColor(RushBoard.getString("messages.unknown-command")));
        }
        return true;
    }
}
