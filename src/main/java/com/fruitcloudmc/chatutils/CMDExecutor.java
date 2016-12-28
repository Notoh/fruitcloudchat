package com.fruitcloudmc.chatutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        PlayerWrapper wrapper = ChatUtils.getCorrespondingWrapper(player);
        if(wrapper == null) {
            Bukkit.getServer().getLogger().severe("");
            return false;
        }
        if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.GREEN + "You must enter a channel to switch to, type /chat <channel> to " +
                    "switch or /chat channels to find out a list you can switch to!");
            return false;
        }
        if(args[0].equalsIgnoreCase("staff")) {
            if(!wrapper.isStaff() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.STAFF);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to STAFF.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("admin")) {
            if(!wrapper.isAdmin() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.ADMIN);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to ADMIN.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("owner")) {
            if(!wrapper.isOwner() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.OWNER);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to OWNER.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("developer")) {
            if(!wrapper.isDev() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.STAFF);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to DEV.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("bt") || args[0].equalsIgnoreCase("buildteam")) {
            if(!wrapper.isBT() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.STAFF);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to BT.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("mod")) {
            if(!wrapper.isMod() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(Channel.STAFF);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to MOD.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("channels")) {
            player.sendMessage(ChatColor.GREEN + "The following are channels you can swithc to: ");
            player.sendMessage(ChatColor.YELLOW  + "ALLCHAT");
            if(wrapper.isBT()) {
                player.sendMessage(ChatColor.AQUA + "BT");
            }
            if(wrapper.isStaff()) {
                player.sendMessage(ChatColor.BLUE + "STAFF");
            }
            if(wrapper.isMod()) {
                player.sendMessage(ChatColor.DARK_GREEN + "MOD");
            }
            if(wrapper.isDev()) {
                player.sendMessage(ChatColor.RED + "DEV");
            }
            if(wrapper.isAdmin()) {
                player.sendMessage(ChatColor.DARK_RED + "ADMIN");
            }
            if(wrapper.isOwner()) {
                player.sendMessage(ChatColor.DARK_PURPLE + "OWNER");
            }
        }

        return true;
    }
}
