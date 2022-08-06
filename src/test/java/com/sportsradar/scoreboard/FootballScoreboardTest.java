package com.sportsradar.scoreboard;

import com.sportsradar.exception.FinishGameException;
import com.sportsradar.exception.GameAlreadyStartedException;
import com.sportsradar.exception.GameSameTeamsException;
import com.sportsradar.game.FootballGame;
import com.sportsradar.game.Game;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UnknownFormatConversionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FootballScoreboardTest {

    static final String TEAM_A = "TEAM_A";
    static final String TEAM_B = "TEAM_B";
    static final String TEAM_C = "TEAM_C";
    static final String TEAM_D = "TEAM_D";

    @Test
    void shouldStartGame_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        String teamATeamBGameId = FootballGame.generateUniqueId(TEAM_A, TEAM_B);
        Map<String, Game> result = new FootballScoreboard().startGame(Optional.of(TEAM_A), Optional.of(TEAM_B), scoreboard);

        assertEquals(1, result.size());
        assertEquals(result.get(teamATeamBGameId).getHomeTeamScore(), 0);
        assertEquals(result.get(teamATeamBGameId).getAwayTeamScore(), 0);
    }

    @Test
    void shouldStartGame_addMultipleGames_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        String teamATeamBGameId = FootballGame.generateUniqueId(TEAM_A, TEAM_B);
        String teamCTeamDGameId = FootballGame.generateUniqueId(TEAM_C, TEAM_D);
        Map<String, Game> updatedScoreboard = new FootballScoreboard().startGame(Optional.of(TEAM_A), Optional.of(TEAM_B), scoreboard);
        Map<String, Game> result = new FootballScoreboard().startGame(Optional.of(TEAM_C), Optional.of(TEAM_D), updatedScoreboard);

        assertEquals(2, result.size());
        assertEquals(result.get(teamATeamBGameId).getHomeTeamScore(), 0);
        assertEquals(result.get(teamATeamBGameId).getHomeTeamScore(), 0);
        assertEquals(result.get(teamCTeamDGameId).getHomeTeamScore(), 0);
        assertEquals(result.get(teamCTeamDGameId).getHomeTeamScore(), 0);

    }

    @Test
    void shouldStartGame_emptyTeamName_failure() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        assertThrows(UnknownFormatConversionException.class, () ->
                new FootballScoreboard().startGame(Optional.of(""), Optional.of(TEAM_A), scoreboard));
    }

    @Test
    void shouldStartGame_startingRunningGame_failure() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(Optional.of(TEAM_A), Optional.of(TEAM_B), scoreboard);

        assertThrows(GameAlreadyStartedException.class, () ->
                new FootballScoreboard().startGame(Optional.of(TEAM_B), Optional.of(TEAM_A), updatedScoreBoard));
    }

    @Test
    void shouldStartGame_sameHomeGameAndAwayGame_failure() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        assertThrows(GameSameTeamsException.class, () ->
                new FootballScoreboard().startGame(Optional.of(TEAM_A), Optional.of(TEAM_A), scoreboard));
    }

    @Test
    void shouldFinishGame_success() throws Exception{
        Map<String, Game> scoreboard = new HashMap();
        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(Optional.of(TEAM_A), Optional.of(TEAM_B), scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(Optional.of(TEAM_C), Optional.of(TEAM_D), updatedScoreBoard);

        updatedScoreBoard = new FootballScoreboard().finishGame(Optional.of(TEAM_B), Optional.of(TEAM_A), updatedScoreBoard);

        assertEquals(1,updatedScoreBoard.size());
        assertEquals(false,Utlity.isGameRunning(TEAM_A,TEAM_B,updatedScoreBoard));
    }

    @Test
    void shouldFinishGame_noGamesToFinish_throws() throws Exception{
        Map<String, Game> scoreboard = new HashMap();

        assertThrows(FinishGameException.class, () -> new FootballScoreboard().finishGame(Optional.of(TEAM_A), Optional.of(TEAM_B), scoreboard));

    }
}
