package com.fruitcloudmc.chatutils;

import org.bukkit.entity.Player;

public class PlayerWrapper {

    private Channel channel;
    private Player corresponding;
    private boolean isStaff;
    private boolean isAdmin;
    private boolean isDev;
    private boolean isBT;
    private boolean isOwner;
    private boolean isMod;
    private CustomChannel custom;

    public PlayerWrapper(Player player) {
        this.channel = Channel.ALLCHAT;
        this.corresponding = player;

        this.isOwner = player.hasPermission("chatutils.owner");
        this.isAdmin = player.hasPermission("chatutils.admin");
        this.isBT = player.hasPermission("chatutils.buildteam");
        this.isDev = player.hasPermission("chatutils.dev");
        this.isStaff = player.hasPermission("chatutils.staff");
        this.isMod = player.hasPermission("chatutils.mod");
    }

    Player getCorresponding() {
        return this.corresponding;
    }

    boolean isStaff() {
        return isStaff;
    }

    boolean isAdmin() {
        return isAdmin;
    }
    boolean isDev() {
        return isDev;
    }
    boolean isBT() {
        return isBT;
    }
    boolean isOwner() {
        return isOwner;
    }
    boolean isMod() {
        return isMod;
    }

    Channel getChannel() {
        return this.channel;
    }

    void setChannel(Channel channel) {
        this.channel = channel;
    }

    void switchCustom(CustomChannel channel) {
        this.channel = Channel.ALLCHAT;
        this.custom = channel;
    }

    CustomChannel getCustom() {
        return this.custom;
    }

}
