package de.marcey.marceyapi.utils;

import de.marcey.marceyapi.objects.RandomPicker;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PlayerManager {

    private Player player;

    public PlayerManager(Player p){
        this.player = p;
    }

    public void kill(){
        getPlayer().damage(getPlayer().getHealth()+9999);
    }

    public Player getPlayer() {
        return player;
    }
}
