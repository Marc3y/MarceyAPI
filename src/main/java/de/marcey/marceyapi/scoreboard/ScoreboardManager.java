
package de.marcey.marceyapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardManager {

    private static final List<ScoreboardCore> SCOREBOARD_CORES = new ArrayList<>();

    public List<ScoreboardCore> getScoreboardCores() {
        return SCOREBOARD_CORES;
    }

    private static ScoreboardManager instance;

    public static ScoreboardManager getInstance() {
        if (instance == null) {
            ScoreboardManager.instance = new ScoreboardManager();
        }
        return instance;
    }

    /**
     * Get a scoreboard core by the provided class
     *
     * @param clazz the class
     * @return the fitting scoreboard core
     */
    public <T extends ScoreboardCore> T getScoreboardCore(Class<T> clazz) {
        // get scoreboardCore by class
        for (ScoreboardCore scoreboardCore : SCOREBOARD_CORES) {
            if (clazz.isInstance(scoreboardCore)) {
                return clazz.cast(scoreboardCore);
            }
        }

        return null;
    }

    /**
     * Get the currently active ScoreboardCore
     *
     * @return the ScoreboardCore
     */
    public ScoreboardCore getActiveScoreboardCore() {
        // loop scoreboardCores
        for (ScoreboardCore scoreboardCore : SCOREBOARD_CORES) {
            if (scoreboardCore.isActive()) {
                return scoreboardCore;
            }
        }

        return null;
    }

    /**
     * Get the Scoreboard object which holds the scoreboard that the player currently sees
     *
     * @param player the player
     * @return the Scoreboard object
     */
    public Scoreboard getCurrentScoreboard(Player player) {
        // loop scoreboardCores
        for (ScoreboardCore scoreboardCore : SCOREBOARD_CORES) {

            // get scoreboard of player
            Scoreboard scoreboard = scoreboardCore.getScoreboard(player);
            if (scoreboard == null) {
                continue;
            }

            // is current scoreboard
            if (player.getScoreboard().equals(scoreboard.getScoreboard())) {
                return scoreboard;
            }
        }

        return null;
    }

    /**
     * copy all team from the main scoreboard to the given scoreboard
     *
     * @param scoreboard the scoreboard of the player
     */
    public void copyTeams(org.bukkit.scoreboard.Scoreboard scoreboard) {
        for (Team team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
            copyTeam(team, scoreboard);
        }
    }

    /**
     * copy the given team from the main scoreboard to all players
     *
     * @param team the team to be copied
     */
    public void copyTeam(Team team) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            copyTeam(team, player.getScoreboard());
        }
    }

    /**
     * copy the given team from the main scoreboard to the given scoreboard
     *
     * @param team       the team to be copied
     * @param scoreboard the scoreboard of the player
     */
    public void copyTeam(Team team, org.bukkit.scoreboard.Scoreboard scoreboard) {
        // Get team in the given scoreboard
        Team team_ = scoreboard.getTeam(team.getName());
        if (team_ == null) {
            team_ = scoreboard.registerNewTeam(team.getName());
        }

        // Settings
        team_.setPrefix(team.getPrefix());
        team_.setSuffix(team.getSuffix());
        team_.setDisplayName(team.getDisplayName());
        team_.setColor(team.getColor());
        team_.setAllowFriendlyFire(team.allowFriendlyFire());
        team_.setCanSeeFriendlyInvisibles(team.canSeeFriendlyInvisibles());

        // Options
        for (Team.Option option : Team.Option.values()) {
            team_.setOption(option, team.getOption(option));
        }

        // Clear entries
        for (String entry : new ArrayList<>(team_.getEntries())) {
            if (!team.getEntries().contains(entry)) {
                team_.removeEntry(entry);
            }
        }

        //Add Entries
        for (String entry : team.getEntries()) {
            team_.addEntry(entry);
        }
    }
}
