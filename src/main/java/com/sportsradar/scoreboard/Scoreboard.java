package com.sportsradar.scoreboard;

import com.sportsradar.exception.*;
import com.sportsradar.game.Game;

import java.util.List;
import java.util.Map;

public interface Scoreboard {
    /**
     *
     * @param game Game object contain game information like team names to be used to start a game.
     * @param games All running games.
     * @return
     * @throws GameAlreadyStartedException
     * @throws GameSameTeamsException
     * @throws InvalidInputException
     */
    Map<String, Game> startGame(Game game, Map<String, Game> games)
            throws GameAlreadyStartedException, GameSameTeamsException, InvalidInputException;

    /**
     *
     * @param game Game object contain game information like team names to be used to  finish a game.
     * @param games All running games.
     * @return
     * @throws InvalidInputException
     * @throws GameAlreadyStartedException
     * @throws GameSameTeamsException
     * @throws FinishGameException
     */

    Map<String, Game> finishGame(Game game, Map<String, Game> games) throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException;

    /**
     * Updates a game score.
     * Each team score is assumed to be greater than or equal to zero.
     * @param game Game object contain game information like team names to be used to  update a game.
     * @param homeTeamScore
     * @param awayTeamScore
     * @param games All running games.
     * @return
     * @throws InvalidInputException
     * @throws GameAlreadyStartedException
     * @throws GameSameTeamsException
     * @throws FinishGameException
     * @throws GameScoreException
     */
    Map<String, Game> updateGameScore(Game game, int homeTeamScore, int awayTeamScore, Map<String, Game> games) throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException, GameScoreException;

    /**
     * Displays summary based on the running games.
     * @param scoreboard  All running games.
     * @return
     */
    List<Game> getSummary(Map<String, Game> scoreboard);
}
