package com.sportsradar.game;

/**
 * Represents a football game.
 */
public final class FootballGame extends Game {

    public FootballGame(String homeTeamName, String awayTeamName) {
        super(homeTeamName, awayTeamName);
    }

    /**
     * @param homeTeamName
     * @param awayTeamName
     * @return a string which uniquely  identifies a game between two teams.
     */

    public static String generateUniqueId(String homeTeamName, String awayTeamName) {
        return homeTeamName.toUpperCase() + "," + awayTeamName.toUpperCase();
    }
}
