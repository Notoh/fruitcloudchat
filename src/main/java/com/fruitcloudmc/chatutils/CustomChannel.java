package com.fruitcloudmc.chatutils;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CustomChannel {

    ArrayList<PlayerWrapper> members = new ArrayList<>();
    Player owner;
    private String name;

    CustomChannel(Player leader, String name) {
        this.name = name;
        this.owner = leader;

        ChatUtils.customChannels.add(this);
        members.add(ChatUtils.getCorrespondingWrapper(leader));
    }

    public void setOwner(Player player) {
        this.owner = player;
    }

    Player getOwner() {
        return this.owner;
    }

    @Override
    public String toString() {
        return name;
    }


}
