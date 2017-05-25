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
            Bukkit.getServer().getLogger().severe("Wrapper of player " + player.getName() + " cannot be found!");
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
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.STAFF);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to STAFF.");
                wrapper.switchCustom(null);
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("admin")) {
            if(!wrapper.isAdmin() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.ADMIN);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to ADMIN.");
                wrapper.switchCustom(null);
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("owner")) {
            if(!wrapper.isOwner() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.OWNER);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to OWNER.");
                wrapper.switchCustom(null);
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("dev") || args[0].equalsIgnoreCase("developer")) {
            if(!wrapper.isDev() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.DEV);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to DEV.");
                wrapper.switchCustom(null);
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("bt") || args[0].equalsIgnoreCase("buildteam")) {
            if(!wrapper.isBT() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.BT);
                wrapper.switchCustom(null);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to BT.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("mod")) {
            if(!wrapper.isMod() || !player.hasPermission("chatutils.switch")) {
                player.sendMessage(ChatColor.GREEN + "You don't have permission to switch to that channel!");
                return true;
            } else {
                wrapper.setChannel(com.fruitcloudmc.chatutils.Channel.MOD);
                wrapper.switchCustom(null);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to MOD.");
                return true;
            }
        }
        if(args[0].equalsIgnoreCase("channels")) {
            player.sendMessage(ChatColor.GREEN + "The following are channels you can switch to: ");
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
            for(com.fruitcloudmc.chatutils.CustomChannel channel : com.fruitcloudmc.chatutils.ChatUtils.customChannels) {
                player.sendMessage(ChatColor.DARK_BLUE + channel.toString());
            }
        }
        if(args[0].equalsIgnoreCase("allchat") || args[0].equalsIgnoreCase("general")) {
            if(wrapper.getChannel() != Channel.ALLCHAT) {
                wrapper.setChannel(Channel.ALLCHAT);
                wrapper.switchCustom(null);
                player.sendMessage(ChatColor.GREEN + "Your channel was switched to ALLCHAT.");
                return true;
            } else {
                player.sendMessage("You're already in allchat!");
                return false;
            }
        }
        if(args[0].equalsIgnoreCase("custom")) {
            if(args[1].equalsIgnoreCase("switch") && args.length == 3) {
                String name = args[2];
                for(CustomChannel channel : ChatUtils.customChannels) {
                    if(channel.toString().equalsIgnoreCase(name)) {
                        wrapper.setChannel(Channel.CUSTOM);
                        wrapper.switchCustom(channel);
                        channel.members.add(wrapper);
                        player.sendMessage(ChatColor.GREEN + "You switched to " + channel.toString() +" custom " +
                                "channel!");
                        return true;
                    }
                }
                player.sendMessage("That channel doesn't exist!");
            }
            if(args[1].equalsIgnoreCase("create") && args.length == 3) {
                String name = args[2];
                CustomChannel channel = new CustomChannel(player,name);
                ChatUtils.customChannels.add(channel);
                wrapper.setChannel(Channel.CUSTOM);
                wrapper.switchCustom(channel);
                for(CustomChannel customChannel : ChatUtils.customChannels) {
                    if(customChannel.toString().equalsIgnoreCase(name)) {
                        player.sendMessage("A channel with that name already exists!");
                        return false;
                    }
                }
                player.sendMessage("You created your channel with name " + name);
            }
            if(args[1].equalsIgnoreCase("delete") && args.length == 2) {
                CustomChannel channel = wrapper.getCustom();
                if(channel != null && channel.getOwner().getName().equals(player.getName())) {
                    for(PlayerWrapper wrap : channel.members) {
                        Player pla = wrap.getCorresponding();
                        pla.sendMessage(ChatColor.GREEN + "The custom channel you were in was deleted so you were " +
                                "switched to ALLCHAT" +
                                ".");
                        wrap.switchCustom(null);
                        wrap.setChannel(com.fruitcloudmc.chatutils.Channel.ALLCHAT);
                    }
                    channel.members.clear();
                    ChatUtils.customChannels.remove(channel);
                    channel.owner = null;
                } else {
                    player.sendMessage(ChatColor.GREEN + "You aren't the owner of a channel!");
                }
            }
        }
        return true;
    }
}
