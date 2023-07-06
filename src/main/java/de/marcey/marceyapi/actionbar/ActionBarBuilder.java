package de.marcey.marceyapi.actionbar;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ActionBarBuilder {

    private String message;
    private int seconds;
    private int cooldown;
    private ActionBarUpdateEvent updateEvent;
    private ActionBarBuilder instance;
    private boolean started;
    private boolean forceStop;

    public ActionBarBuilder(String message){
        instance = this;
        this.forceStop = false;
        this.message = message;
        this.seconds = -1;
    }

    public ActionBarBuilder setSeconds(int seconds){
        this.seconds = seconds;
        this.cooldown = seconds;
        return this;
    }

    public ActionBarBuilder onUpdateActionbar(ActionBarUpdateEvent event){
        this.updateEvent = event;
        return this;
    }

    public ActionBarBuilder setMessage(String message){
        this.message = message;
        return this;
    }

    public void start(Player p){
        if(isStarted()) return;
        if(seconds <= 1){
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            return;
        }
        setStarted(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(forceStop){
                    forceStop = false;
                    this.cancel();
                }
                updateEvent.onUpdate(instance);
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                if(cooldown <= 0) {
                    setStarted(false);
                    this.cancel();
                }
                cooldown--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void startForPlayers(Player... player){
        if(isStarted()) return;
        if(seconds <= 1){
            for(Player p : player) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
            return;
        }
        setStarted(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(forceStop){
                    forceStop = false;
                    this.cancel();
                }
                updateEvent.onUpdate(instance);
                for(Player p : player) {
                    if(p == null) continue;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
                if(cooldown <= 0) {
                    setStarted(false);
                    this.cancel();
                }
                cooldown--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void startForPlayers(List<Player> player){
        if(isStarted()) return;
        if(seconds <= 1){
            for(Player p : player) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
            return;
        }
        setStarted(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(forceStop){
                    forceStop = false;
                    this.cancel();
                }
                updateEvent.onUpdate(instance);
                for(Player p : player) {
                    if(p == null) continue;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
                if(cooldown <= 0) {
                    setStarted(false);
                    this.cancel();
                }
                cooldown--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void startForAll(){
        if(isStarted()) return;
        if(seconds <= 1){
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
            }
            return;
        }
        setStarted(true);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(forceStop){
                    forceStop = false;
                    this.cancel();
                }
                updateEvent.onUpdate(instance);
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(p == null) continue;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                }
                if(cooldown <= 0) {
                    setStarted(false);
                    this.cancel();
                }
                cooldown--;
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 20);
    }

    public void stop(){
        this.forceStop = true;
    }

    public void addCooldown(int secondsToAdd){
        this.seconds = seconds+secondsToAdd;
        this.cooldown = this.cooldown + secondsToAdd;
    }

    public void removeCooldown(int secondsToRemove){
        this.seconds = seconds-secondsToRemove;
        this.cooldown = this.cooldown - secondsToRemove;
    }

    public void setCooldown(int seconds){
        this.seconds = seconds;
        this.cooldown = seconds;
    }

    public String getMessage() {
        return message;
    }

    private void setStarted(boolean started) {
        this.started = started;
    }

    public int getCurrentCooldown() {
        return cooldown;
    }

    private int getSeconds() {
        return seconds;
    }

    public boolean isStarted() {
        return started;
    }
}
