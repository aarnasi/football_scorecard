package com.sportsradar.scoreboard;

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
}
