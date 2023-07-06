package de.marcey.marceyapi.bossbar;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BossBarBuilder {

    private String title;
    private int durationSeconds;
    private BarColor color;
    private BarStyle barStyle;
    private BossBar bossBar;
    public static List<BossBarBuilder> bossbars = new ArrayList<>();

    public BossBarBuilder(String title, int durationSeconds, BarColor color, BarStyle barStyle) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.color = color;
        this.barStyle = barStyle;
        bossbars.add(this);
    }

    public BossBarBuilder(String title, int durationSeconds, BarColor color) {
        this.title = title;
        this.durationSeconds = durationSeconds;
        this.color = color;
        this.barStyle = BarStyle.SOLID;
        bossbars.add(this);
    }

    public void start(Player... p){
        this.bossBar = Bukkit.createBossBar(this.title, color, barStyle);
        for(Player current : p){
            this.bossBar.addPlayer(current);
        }
        bossbars.add(getBuilder());
        int ticks = durationSeconds * 20;
        float progressPerTick = 1.0f / ticks;
        new BukkitRunnable(){
            float currentProgress = 1.0f;
            @Override
            public void run() {
                if(!getBossbars().contains(getBuilder())){
                    this.cancel();
                }
                currentProgress = currentProgress-progressPerTick;
                if(currentProgress <= 0.001667603){
                    getBossBar().setVisible(false);
                    getBossBar().removeAll();
                    bossbars.remove(getBuilder());
                    this.cancel();
                }
                getBossBar().setProgress(currentProgress);
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 1);
    }

    public void start(Player p){
        this.bossBar = Bukkit.createBossBar(this.title, color, barStyle);
        this.bossBar.addPlayer(p);
        bossbars.add(getBuilder());
        int ticks = durationSeconds * 20;
        float progressPerTick = 1.0f / ticks;
        new BukkitRunnable(){
            float currentProgress = 1.0f;
            @Override
            public void run() {
                if(!getBossbars().contains(getBuilder())){
                    this.cancel();
                }
                currentProgress = currentProgress-progressPerTick;
                if(currentProgress <= 0.001667603){
                    getBossBar().setVisible(false);
                    getBossBar().removeAll();
                    bossbars.remove(getBuilder());
                    this.cancel();
                }
                getBossBar().setProgress(currentProgress);
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 1);
    }

    public void start(List<Player> players){
        this.bossBar = Bukkit.createBossBar(this.title, color, barStyle);
        for(Player current : players){
            this.bossBar.addPlayer(current);
        }
        bossbars.add(getBuilder());
        int ticks = durationSeconds * 20;
        float progressPerTick = 1.0f / ticks;
        new BukkitRunnable(){
            float currentProgress = 1.0f;
            @Override
            public void run() {
                if(!getBossbars().contains(getBuilder())){
                    this.cancel();
                }
                currentProgress = currentProgress-progressPerTick;
                if(currentProgress <= 0.001667603){
                    getBossBar().setVisible(false);
                    getBossBar().removeAll();
                    bossbars.remove(getBuilder());
                    this.cancel();
                }
                getBossBar().setProgress(currentProgress);
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 1);
    }

    public void startForAll(){
        this.bossBar = Bukkit.createBossBar(this.title, color, barStyle);
        for(Player current : Bukkit.getOnlinePlayers()){
            this.bossBar.addPlayer(current);
        }
        bossbars.add(getBuilder());
        int ticks = durationSeconds * 20;
        float progressPerTick = 1.0f / ticks;
        new BukkitRunnable(){
            float currentProgress = 1.0f;
            @Override
            public void run() {
                if(!getBossbars().contains(getBuilder())){
                    this.cancel();
                }
                currentProgress = currentProgress-progressPerTick;
                if(currentProgress <= 0.001667603){
                    getBossBar().setVisible(false);
                    getBossBar().removeAll();
                    bossbars.remove(getBuilder());
                    this.cancel();
                }
                getBossBar().setProgress(currentProgress);
            }
        }.runTaskTimer(MarceyMinecraftAPI.getPlugin(), 0, 1);
    }

    public void cancel(){
        getBossbars().remove(this);
    }

    public static List<BossBarBuilder> getBossbars() {
        return bossbars;
    }

    public static void setBossbars(List<BossBarBuilder> bossbars) {
        BossBarBuilder.bossbars = bossbars;
    }

    public BossBarBuilder getBuilder(){
        return this;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public void setBossBar(BossBar bossBar) {
        this.bossBar = bossBar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public BarColor getColor() {
        return color;
    }

    public void setColor(BarColor color) {
        this.color = color;
    }

    public BarStyle getBarStyle() {
        return barStyle;
    }

    public void setBarStyle(BarStyle barStyle) {
        this.barStyle = barStyle;
    }
}
