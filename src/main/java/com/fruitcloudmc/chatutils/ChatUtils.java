package com.fruitcloudmc.chatutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Created by Notoh for FruitCloudMC.
 */
public class ChatUtils extends JavaPlugin implements Listener {

    private static ArrayList<PlayerWrapper> wrappers = new ArrayList<>();
    static ArrayList<CustomChannel> customChannels = new ArrayList<>();


    public void onEnable() {
        getServer().getLogger().info("ChatUtils is enabled!");
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getCommand("chat").setExecutor(new CMDExecutor());
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(getCorrespondingWrapper(e.getPlayer()) == null) {
            PlayerWrapper wrapper = new PlayerWrapper(e.getPlayer());
            wrappers.add(wrapper);
        }
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerQuit(PlayerQuitEvent e) {
        if(wrappers.contains(getCorrespondingWrapper(e.getPlayer()))) {
            wrappers.remove(getCorrespondingWrapper(e.getPlayer()));
        }
    }

    static PlayerWrapper getCorrespondingWrapper(Player player) {
        for(PlayerWrapper playerWrapper : wrappers) {
            if(playerWrapper.getCorresponding().equals(player)) {
                return playerWrapper;
            }
        }
        return null;
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        PlayerWrapper wrapper = getCorrespondingWrapper(e.getPlayer());
        if(wrapper != null) {
            Channel channel =  wrapper.getChannel();
            if(wrapper.getChannel() != Channel.CUSTOM) {
                 e.setMessage(convertPrefix(channel) + e.getMessage());
            } else {
                e.setMessage(wrapper.getCustom().getOwner().getName() + "'s Channel > " + e.getMessage());
            }
            for(Player player : Bukkit.getOnlinePlayers()) {
                PlayerWrapper playerWrapper = getCorrespondingWrapper(player);
                if(playerWrapper != null) {
                    if(channel == Channel.OWNER && !playerWrapper.isOwner()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.ADMIN && !playerWrapper.isAdmin()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.STAFF && !playerWrapper.isStaff()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.DEV && !playerWrapper.isDev()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.BT && !playerWrapper.isBT()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.MOD && !playerWrapper.isMod()) {
                        e.getRecipients().remove(player);
                    }
                    if(channel == Channel.CUSTOM && playerWrapper.getCustom() != wrapper.getCustom()) {
                        e.getRecipients().remove(player);
                    }
                }
            }
        }
    }

    private String convertPrefix(Channel channel) {
        if(channel == Channel.STAFF) {
            return ChatColor.BLUE + "StaffChat > ";
        } else if(channel == Channel.ADMIN) {
            return ChatColor.DARK_RED + "AdminChat > ";
        } else if(channel == Channel.OWNER) {
            return ChatColor.DARK_PURPLE + "OwnerChat > ";
        } else if(channel == Channel.DEV) {
            return ChatColor.RED + "DevChat > ";
        } else if(channel == Channel.MOD) {
            return ChatColor.GREEN + "ModChat > ";
        } else if(channel == Channel.BT) {
            return ChatColor.AQUA + "BTChat > ";
        } else {
            return "";
        }
    }
}