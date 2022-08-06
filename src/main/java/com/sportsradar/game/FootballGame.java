package com.sportsradar.game;

import java.util.Optional;

/**
 * Represents a football game.
 */
public final class FootballGame extends Game {

    public FootballGame(Optional<String> homeTeamName, Optional<String> awayTeamName) {
        super();
        super.setHomeTeamName(homeTeamName);
        super.setAwayTeamName(awayTeamName);
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
