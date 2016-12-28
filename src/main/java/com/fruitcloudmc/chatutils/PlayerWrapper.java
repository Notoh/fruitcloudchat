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

    public PlayerWrapper(Player player) {
        this.channel = Channel.ALLCHAT;
        this.corresponding = player;
        if(player.hasPermission("chatutils.owner")) {
            this.isOwner = true;
        } else {
            this.isOwner = false;
        }
        if(player.hasPermission("chatutils.admin")) {
            this.isAdmin = true;
        } else {
            this.isAdmin = false;
        }
        if(player.hasPermission("chatutils.buildteam")) {
            this.isBT = true;
        } else {
            this.isBT = false;
        }
        if(player.hasPermission("chatutils.dev")) {
            this.isDev = true;
        } else {
            this.isDev = false;
        }
        if(player.hasPermission("chatutils.staff")) {
            this.isStaff = true;
        } else {
            this.isStaff = false;
        }
        if(player.hasPermission("chatutils.mod")) {
            this.isMod = true;
        } else {
            this.isMod = false;
        }
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


}
