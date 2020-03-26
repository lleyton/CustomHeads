package com.innatical.HeadPicker;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

class HeadsCommand implements CommandExecutor {
    private final HeadDatabase db;
    private final Plugin plugin;
    private final Server server;

    public HeadsCommand(HeadDatabase db, Plugin plugin, Server server) {
        this.db = db;
        this.plugin = plugin;
        this.server = server;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!db.isReady()) {
                player.sendMessage("Sorry! The database is currently syncing, please try again in a moment.");
                return true;
            }
            new MainView(db, plugin, server).openInventory(player);
        } else {
            sender.sendMessage("This command can only be executed by players!");
        }
        return true;
    }
}
