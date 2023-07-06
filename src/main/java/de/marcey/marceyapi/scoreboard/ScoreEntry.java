
package de.marcey.marceyapi.scoreboard;

import javax.annotation.Nullable;

public class ScoreEntry {

    private final String teamName;

    private String value;

    private int score;

    public ScoreEntry(@Nullable String teamName, String value, int score) {
        this.teamName = teamName;
        this.value = value;
        this.score = score;
    }

    // getter and setter
    public String getTeamName() {
        return teamName;
    }

    public String getValue() {
        return value;
    }

    public ScoreEntry setValue(String value) {
        this.value = value;
        return this;
    }

    public int getScore() {
        return score;
    }

    public ScoreEntry setScore(int score) {
        this.score = score;
        return this;
    }
}
