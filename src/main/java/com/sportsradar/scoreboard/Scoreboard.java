package com.sportsradar.scoreboard;

import com.sportsradar.exception.*;
import com.sportsradar.game.Game;

import java.util.Map;

public interface Scoreboard {
    Map<String, Game> startGame(Game game, Map<String, Game> games)
            throws GameAlreadyStartedException, GameSameTeamsException, InvalidInputException;

    Map<String, Game> finishGame(Game game, Map<String, Game> games) throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException;

    Map<String, Game> updateGameScore(String gameId, int homeTeamScore, int awayTeamScore, Map<String, Game> games) throws InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, FinishGameException, GameScoreException;
}
