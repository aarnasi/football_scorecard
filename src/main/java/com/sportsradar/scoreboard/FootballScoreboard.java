package com.sportsradar.scoreboard;

import com.sportsradar.exception.GameAlreadyStartedException;
import com.sportsradar.exception.GameSameTeamsException;
import com.sportsradar.exception.InvalidInputException;
import com.sportsradar.game.FootballGame;
import com.sportsradar.game.Game;

import java.util.*;

/***
 * Enables scoreboard management for a football world cup game.
 * The game is supposed to be played between two teams only.
 * The team names i.e. home team and away team should be unique.
 * If the game is already, starting again throws exception.
 */
public final class FootballScoreboard implements Scoreboard {
    /**
     * @param homeTeamName
     * @param awayTeamName
     * @param scoreboard   A map containing unique game Id and game information.
     * @return Updated scoreboard
     * @throws GameAlreadyStartedException
     * @throws GameSameTeamsException
     */
    public Map<String, Game> startGame(final Optional<String> homeTeamName, final Optional<String> awayTeamName, final Map<String, Game> scoreboard)
            throws GameAlreadyStartedException, GameSameTeamsException, InvalidInputException {
        validateInput(homeTeamName, awayTeamName, scoreboard);
        Map<String, Game> copyOfScoreboard = new HashMap();
        copyOfScoreboard.putAll(scoreboard);
        FootballGame footballGame = new FootballGame(homeTeamName, awayTeamName);
        footballGame.setHomeTeamScore(0);
        footballGame.setAwayTeamScore(0);
        String gameId = FootballGame.generateUniqueId(homeTeamName.get(), awayTeamName.get());
        copyOfScoreboard.put(gameId, footballGame);
        return Collections.unmodifiableMap(copyOfScoreboard);
    }

    private void validateInput(final Optional<String> homeTeamName, final Optional<String> awayTeamName, final Map<String, Game> scoreboard)
            throws GameSameTeamsException, GameAlreadyStartedException, InvalidInputException {
        String homeTeam = homeTeamName.orElseThrow(() -> new InvalidInputException("no value provided for home team name"));
        String awayTeam = awayTeamName.orElseThrow(() -> new InvalidInputException("no value provided for home team name"));
        Optional.of(scoreboard).orElseThrow(() -> new InvalidInputException("scoreboard in illegal state"));

        if (homeTeam.isEmpty()) {
            throw new UnknownFormatConversionException("home team name is empty ");
        }
        if (awayTeam.isEmpty()) {
            throw new UnknownFormatConversionException("away team name is empty ");
        }
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new GameSameTeamsException();
        }

        String gameId1 = FootballGame.generateUniqueId(homeTeam, awayTeam);
        String gameId2 = FootballGame.generateUniqueId(awayTeam, homeTeam);
        if (scoreboard.get(gameId1) != null
                || scoreboard.get(gameId2) != null) {
            throw new GameAlreadyStartedException();
        }

    }
}
