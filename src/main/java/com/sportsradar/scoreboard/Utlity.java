package com.sportsradar.scoreboard;

import com.sportsradar.exception.GameNotFoundException;
import com.sportsradar.game.FootballGame;
import com.sportsradar.game.Game;

import java.util.Map;

public class Utlity {

    static boolean isGameRunning(String homeTeamName, String awayTeamName, Map<String, Game> scoreboard){
        if (scoreboard.containsKey(FootballGame.generateUniqueId(homeTeamName,awayTeamName)) ||
                scoreboard.containsKey(FootballGame.generateUniqueId(awayTeamName,homeTeamName))){
            return true;
        }
        return false;
    }
    static String getRunningGameId(String homeTeamName, String awayTeamName, Map<String, Game> scoreboard) throws GameNotFoundException {
        if (scoreboard.containsKey(FootballGame.generateUniqueId(homeTeamName,awayTeamName))) {
            return FootballGame.generateUniqueId(homeTeamName, awayTeamName);
        }else if (scoreboard.containsKey(FootballGame.generateUniqueId(awayTeamName,homeTeamName))){
            return FootballGame.generateUniqueId(awayTeamName,homeTeamName);
        }else throw new GameNotFoundException(String.format("No game running between %s & %s", homeTeamName,awayTeamName));
    }

    static Game getRunningGame(String homeTeamName, String awayTeamName, Map<String, Game> scoreboard) throws Exception {
        if (scoreboard.containsKey(FootballGame.generateUniqueId(homeTeamName, awayTeamName))) {
            return scoreboard.get(FootballGame.generateUniqueId(homeTeamName, awayTeamName));
        } else if (scoreboard.containsKey(FootballGame.generateUniqueId(awayTeamName, homeTeamName))) {
            return scoreboard.get(FootballGame.generateUniqueId(awayTeamName, homeTeamName));
        } else throw new GameNotFoundException(String.format("No game running between %s & %s", homeTeamName,awayTeamName));
    }
}
