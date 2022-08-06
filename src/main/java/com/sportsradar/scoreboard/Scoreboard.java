package com.sportsradar.scoreboard;

import com.sportsradar.exception.GameAlreadyStartedException;
import com.sportsradar.exception.GameSameTeamsException;
import com.sportsradar.exception.InvalidInputException;
import com.sportsradar.game.Game;

import java.util.Map;
import java.util.Optional;

public interface Scoreboard {
    Map<String, Game> startGame(Optional<String> homeTeamName, Optional<String> awayTeamName, Map<String, Game> games)
            throws GameAlreadyStartedException, GameSameTeamsException, InvalidInputException;
}
