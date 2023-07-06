package de.marcey.marceyapi.utils;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;

public class MessageManager {

    private final String prefix;
    private HashMap<String, String> messageValues;

    public MessageManager(String prefix){
        this.prefix = prefix;
        this.messageValues = new HashMap<>();
    }

    public MessageManager addMessage(String value, String message){
        messageValues.put(value, message);
        return this;
    }

    public String getMessage(String value){
        return messageValues.get(value);
    }

    public void sendMessage(Player p, String message){
        if(p == null) return;
        p.sendMessage(getPrefix() + " " + message);
    }

    public void broadcastMessage(String message){
        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendMessage(message);
        });
    }

    public void sendActionbarMessage(Player p, String message, boolean withPrefix){
        if(p == null) return;
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((withPrefix ? getPrefix() + " " : "") + message));
    }

    public void sendActionbarMessage(Player p, String message, boolean withPrefix, int seconds){
        new BukkitRunnable(){
            int secs = seconds;
            @Override
            public void run() {
                if(secs <= 0){
                    this.cancel();
                }
                sendActionbarMessage(p, message, withPrefix);
                secs--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void broadcastActionbarMessage(String message, boolean withPrefix, int seconds){
        new BukkitRunnable(){
            int secs = seconds;
            @Override
            public void run() {
                if(secs <= 0){
                    this.cancel();
                }
                broadcastActionbarMessage(message, withPrefix);
                secs--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int time, int fadeOut){
        if(p == null) return;
        p.sendTitle(title, subtitle, fadeIn, time, fadeOut);
    }
    public void sendTitle(Player p, String title, int fadeIn, int time, int fadeOut){
        if(p == null) return;
        p.sendTitle(title, "§7", fadeIn, time, fadeOut);
    }

    public void sendTitle(Player p, String title, String subtitle, int time){
        if(p == null) return;
        p.sendTitle(title, subtitle, 10, time, 20);
    }
    public void sendTitle(Player p, String title, int time){
        if(p == null) return;
        p.sendTitle(title, "§7", 10, time, 20);
    }

    public void broadcastMessage(String title, String subtitle, int fadeIn, int time, int fadeOut){
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.sendTitle(title, subtitle, fadeIn, time, fadeOut);
        });
    }

    public void broadcastActionbarMessage(String message, boolean withPrefix){
        if(Bukkit.getOnlinePlayers().size() == 0) return;
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText((withPrefix ? getPrefix() + " " : "") + message));
        });
    }

    public String getPrefix() {
        return prefix;
    }
}
