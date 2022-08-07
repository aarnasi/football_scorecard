package com.sportsradar.scoreboard;

import com.sportsradar.exception.*;
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
    public Map<String, Game> startGame(final Optional<String> homeTeamName, final Optional<String> awayTeamName, final Map<String, Game> scoreboard) throws GameAlreadyStartedException, GameSameTeamsException, InvalidInputException {
        validateInput(homeTeamName, awayTeamName, scoreboard);
        String gameId1 = FootballGame.generateUniqueId(homeTeamName.get(), awayTeamName.get());
        String gameId2 = FootballGame.generateUniqueId(awayTeamName.get(), homeTeamName.get());
        if (scoreboard.get(gameId1) != null || scoreboard.get(gameId2) != null) {
            throw new GameAlreadyStartedException();
        }
        Map<String, Game> copyOfScoreboard = new HashMap();
        copyOfScoreboard.putAll(scoreboard);
        FootballGame footballGame = new FootballGame(homeTeamName, awayTeamName);
        footballGame.setHomeTeamScore(0);
        footballGame.setAwayTeamScore(0);
        String gameId = FootballGame.generateUniqueId(homeTeamName.get(), awayTeamName.get());
        copyOfScoreboard.put(gameId, footballGame);
        return Collections.unmodifiableMap(copyOfScoreboard);
    }

    @Override
    public Map<String, Game> finishGame(Optional<String> homeTeamName, Optional<String> awayTeamName, Map<String, Game> scoreboard)
            throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException {
        validateInput(homeTeamName, awayTeamName, scoreboard);
        Map<String, Game> copyOfScoreboard = new HashMap();
        copyOfScoreboard.putAll(scoreboard);
        String gameId1 = FootballGame.generateUniqueId(homeTeamName.get(), awayTeamName.get());
        String gameId2 = FootballGame.generateUniqueId(awayTeamName.get(), homeTeamName.get());
        if(copyOfScoreboard.containsKey(gameId1)){
            copyOfScoreboard.remove(gameId1);
        }else if (copyOfScoreboard.containsKey(gameId2)){
            copyOfScoreboard.remove(gameId2);
        }else {
            throw new FinishGameException(String.format("Game between %s and %s is not running so that it can be finished",
                    homeTeamName.get(), awayTeamName.get()));
        }
        return Collections.unmodifiableMap(copyOfScoreboard);
    }

    @Override
    public Map<String, Game> updateGameScore(final String gameId, final int homeTeamScore, final int awayTeamScore, final Map<String, Game> scoreboard) throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException, GameScoreException {
        Map<String, Game> copyOfScoreboard = new HashMap();
        int existingGameScore = scoreboard.get(gameId).getHomeTeamScore() + scoreboard.get(gameId).getAwayTeamScore();
        int updatedGameScore = homeTeamScore + awayTeamScore;
        if(existingGameScore > updatedGameScore){
              throw new GameScoreException("Score cannot be updated. Updated game score less than the existing game score");
        }
        copyOfScoreboard.putAll(scoreboard);
        copyOfScoreboard.get(gameId).setHomeTeamScore(homeTeamScore);
        copyOfScoreboard.get(gameId).setAwayTeamScore(awayTeamScore);
        return Collections.unmodifiableMap(copyOfScoreboard);
    }

    private void validateInput(final Optional<String> homeTeamName, final Optional<String> awayTeamName, final Map<String, Game> scoreboard) throws GameSameTeamsException, GameAlreadyStartedException, InvalidInputException {
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

    }
}
