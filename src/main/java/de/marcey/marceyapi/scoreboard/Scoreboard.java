
package de.marcey.marceyapi.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public abstract class Scoreboard {

    // API this class

    // player
    private final Player player;

    // core
    private final ScoreboardCore core;

    // scoreboard
    private final org.bukkit.scoreboard.Scoreboard scoreboard;
    private final Objective objective;

    private Team team;

    public Scoreboard(Player player, ScoreboardCore core, String objectiveName, String displayName) {
        this.player = player;
        this.core = core;

        // create and set new scoreboard
        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }

        // get scoreboard
        this.scoreboard = player.getScoreboard();

        // create objective
        Objective objective = this.scoreboard.getObjective(objectiveName);

        // check if objective exists; if not -> create new
        if (objective == null) {
            objective = this.scoreboard.registerNewObjective(objectiveName, "dummy", displayName);
        }

        // get objective
        this.objective = objective;

        // team
        this.team = loadTeam();
    }

    public void create() {
        // add to list in core
        core.getScoreboards().add(this);
    }

    public void destroy() {
        // unregister objective
        objective.unregister();

        // remove from list in core
        core.getScoreboards().remove(this);
    }

    /**
     * Update displaySlot of objective so that the player can see it
     */
    public void show() {
        if (this.objective.getDisplaySlot() != DisplaySlot.SIDEBAR) {
            this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }
    }

    /**
     * Add line to scoreboard
     *
     * @param entry the scoreEntry to be added
     */
    public void addLine(ScoreEntry entry) {

        // check if team is null?
        if (entry.getTeamName() == null) {

            // add line without team name
            this.objective.getScore(entry.getValue()).setScore(entry.getScore());

        } else {

            // create team
            Team team = getTeam(entry.getTeamName());

            // set suffix of team
            team.setSuffix(entry.getValue());

            // add line with team name
            this.objective.getScore(entry.getTeamName()).setScore(entry.getScore());
        }

    }

    /**
     * Get a team by the name
     *
     * @param name the name of the team
     * @return the existing team with the name or a new team with the name
     */
    private Team getTeam(String name) {

        // get team
        Team team = this.scoreboard.getTeam(name);

        // if team exists -> return team
        if (team != null) {
            return team;
        }

        // else, create new team and return it
        team = this.scoreboard.registerNewTeam(name);
        team.addEntry(name);

        // return team
        return team;
    }

    /**
     * Delete the team with the given name
     *
     * @param name the name of the team to be deleted
     */
    public void deleteTeam(String name) {

        // get team
        Team team = this.scoreboard.getTeam(name);

        // if team exists -> delete it
        if (team != null) {

            // reset scores
            this.scoreboard.resetScores(name);

            // unregister team
            team.unregister();
        }
    }

    /**
     * Reset the score of the given value
     *
     * @param value the value to be deleted
     */
    public void deleteLine(String value) {
        this.scoreboard.resetScores(value);
    }

    /**
     * Load the team of all online players
     */
    public void loadTeams() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            loadTeam(player);
        }
    }

    /**
     * Load the team of the given player
     *
     * @param player the player
     */
    public abstract void loadTeam(Player player);

    /**
     * Load the team of the current player
     *
     * @return the team of the player
     */
    public abstract Team loadTeam();

    /**
     * Loads the team of the current player and copies it to every scoreboard
     */
    public void copyTeam() {
        // reload team
        this.team = loadTeam();

        // copy team
        ScoreboardManager.getInstance().copyTeam(this.team);
    }

    // getter
    public Player getPlayer() {
        return player;
    }

    public Team getTeam() {
        return team;
    }

    public ScoreboardCore getCore() {
        return core;
    }

    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}
