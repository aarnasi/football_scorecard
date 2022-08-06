package com.sportsradar.game;

import java.util.Optional;

/**
 * Define a game played between two teams.
 */
public class Game {
    private Optional<String> homeTeamName;
    private Optional<String> awayTeamName;
    private int homeTeamScore;
    private int awayTeamScore;

    public Optional<String> getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(Optional<String> homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Optional<String> getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(Optional<String> awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }
}
