package de.marcey.marceyapi.scoreboard;

import de.marcey.marceyapi.MarceyMinecraftAPI;
import de.marcey.marceyapi.runnable.MarceyRunnable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreboardCore {

    // list to collect all scoreboards
    private final List<Scoreboard> SCOREBOARDS = new ArrayList<>();

    // update task
    private BukkitTask updateTask;

    // currently active
    private boolean active;

    public void create() {
        ScoreboardManager.getInstance().getScoreboardCores().add(this);
    }

    public void destroy() {
        ScoreboardManager.getInstance().getScoreboardCores().remove(this);
    }

    /**
     * start updating
     */
    public void start() {
        if (updateTask != null) {
            return;
        }

        // run task
        updateTask = MarceyRunnable.runTaskTimer(this::update, 0, 1);

        // set active
        this.active = true;
    }

    /**
     * stop updating
     */
    public void stop() {
        if (updateTask == null) {
            return;
        }

        updateTask.cancel();
        updateTask = null;

        // set inactive
        this.active = false;
    }

    /**
     * Update
     */
    public void update() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            update(player);
        }
    }

    public abstract void update(Player player);

    /**
     * Get the scoreboard of the given player
     * @param player the player
     * @return the scoreboard of the player or null, if he doesn't have one already
     */
    public Scoreboard getScoreboard(Player player) {

        // loop board
        for (Scoreboard scoreboard : SCOREBOARDS) {

            // is from player
            if (scoreboard.getPlayer().getUniqueId().equals(player.getUniqueId())) {
                return scoreboard;
            }
        }


        return null;
    }

    // getter
    public List<Scoreboard> getScoreboards() {
        return SCOREBOARDS;
    }

    public boolean isActive() {
        return active;
    }
}
