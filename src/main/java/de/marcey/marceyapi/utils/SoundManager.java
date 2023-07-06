package de.marcey.marceyapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundManager {

    private float defaultVolume;
    private float defaultPitch;

    public SoundManager(float defaultVolume, float pitch){
        this.defaultVolume = defaultVolume;
        this.defaultPitch = pitch;
    }

    public void playSound(Player p, Sound sound){
        if(p == null) return;
        p.playSound(p.getLocation(), sound, getDefaultVolume(), getDefaultPitch());
    }
    public void playSound(Player p, Sound sound, float volume){
        if(p == null) return;
        p.playSound(p.getLocation(), sound, volume, getDefaultPitch());
    }
    public void playSound(Player p, Sound sound, float volume, float pitch){
        if(p == null) return;
        p.playSound(p.getLocation(), sound, volume, pitch);
    }

    public void playSoundForAll(Sound sound){
        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), sound, getDefaultVolume(), getDefaultPitch());
        });
    }
    public void playSoundForAll(Sound sound, float volume){
        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), sound, volume, getDefaultPitch());
        });
    }
    public void playSoundForAll(Sound sound, float volume, float pitch){
        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), sound, volume, pitch);
        });
    }

    public float getDefaultPitch() {
        return defaultPitch;
    }

    public float getDefaultVolume() {
        return defaultVolume;
    }
}
