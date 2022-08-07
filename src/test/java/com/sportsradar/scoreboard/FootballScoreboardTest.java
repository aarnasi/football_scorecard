package com.sportsradar.scoreboard;

import com.sportsradar.exception.FinishGameException;
import com.sportsradar.exception.GameAlreadyStartedException;
import com.sportsradar.exception.GameSameTeamsException;
import com.sportsradar.exception.GameScoreException;
import com.sportsradar.game.FootballGame;
import com.sportsradar.game.Game;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FootballScoreboardTest {

    static final String TEAM_A = "TEAM_A";
    static final String TEAM_B = "TEAM_B";
    static final String TEAM_C = "TEAM_C";
    static final String TEAM_D = "TEAM_D";
    static final String TEAM_E = "TEAM_E";
    static final String TEAM_F = "TEAM_F";

    static final String TEAM_G = "TEAM_G";
    static final String TEAM_H = "TEAM_H";
    static final String TEAM_I = "TEAM_I";
    static final String TEAM_J = "TEAM_J";

    static final Game GAMEAB = new Game(TEAM_A, TEAM_B);
    static final Game GAMECD = new Game(TEAM_C, TEAM_D);
    static final Game GAMEEF = new Game(TEAM_E, TEAM_F);
    static final Game GAMEGH = new Game(TEAM_G, TEAM_H);
    static final Game GAMEIJ = new Game(TEAM_I, TEAM_J);

    @Test
    void shouldStartGame_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        String teamATeamBGameId = FootballGame.generateUniqueId(TEAM_A, TEAM_B);
        Map<String, Game> result = new FootballScoreboard().startGame(GAMEAB, scoreboard);

        assertEquals(1, result.size());
        assertEquals(result.get(teamATeamBGameId).getHomeTeamScore(), 0);
        assertEquals(result.get(teamATeamBGameId).getAwayTeamScore(), 0);
    }

    @Test
    void shouldStartGame_addMultipleGames_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        String teamATeamBGameId = FootballGame.generateUniqueId(TEAM_A, TEAM_B);
        String teamCTeamDGameId = FootballGame.generateUniqueId(TEAM_C, TEAM_D);
        Map<String, Game> updatedScoreboard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        Map<String, Game> result = new FootballScoreboard().startGame(GAMECD, updatedScoreboard);

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
                new FootballScoreboard().startGame(new Game("", TEAM_A), scoreboard));
    }

    @Test
    void shouldStartGame_startingRunningGame_failure() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);

        assertThrows(GameAlreadyStartedException.class, () ->
                new FootballScoreboard().startGame(GAMEAB, updatedScoreBoard));
    }

    @Test
    void shouldStartGame_sameHomeGameAndAwayGame_failure() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        assertThrows(GameSameTeamsException.class, () ->
                new FootballScoreboard().startGame(new Game(TEAM_A, TEAM_A), scoreboard));
    }

    @Test
    void shouldFinishGame_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();
        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMECD, updatedScoreBoard);

        updatedScoreBoard = new FootballScoreboard().finishGame(GAMEAB, updatedScoreBoard);

        assertEquals(1, updatedScoreBoard.size());
        assertEquals(false, Utlity.isGameRunning(TEAM_A, TEAM_B, updatedScoreBoard));
    }

    @Test
    void shouldFinishGame_noGamesToFinish_throws() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        assertThrows(FinishGameException.class, () -> new FootballScoreboard().finishGame(GAMEAB, scoreboard));

    }

    @Test
    void shouldUpdateGameScore_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();
        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMECD, updatedScoreBoard);

        updatedScoreBoard = new FootballScoreboard().updateGameScore(new Game(TEAM_A, TEAM_B), 1, 5, updatedScoreBoard);

        assertEquals(Utlity.getRunningGame(TEAM_A, TEAM_B, updatedScoreBoard).getHomeTeamScore(), 1);
        assertEquals(Utlity.getRunningGame(TEAM_A, TEAM_B, updatedScoreBoard).getAwayTeamScore(), 5);
    }

    @Test
    void shouldUpdateGameScore_updatedScoreLessThenPrevious_throws() throws Exception {
        Map<String, Game> scoreboard = new HashMap();
        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMECD, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEAB, 1, 5, updatedScoreBoard);
        Map<String, Game> immutableScoreboard = Collections.unmodifiableMap(updatedScoreBoard);
        assertThrows(GameScoreException.class, () -> new FootballScoreboard().updateGameScore(GAMEAB, 0, 4, immutableScoreboard));
    }

    @Test
    void shouldSortGames_withLatestGameAddedGameFirst_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMECD, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMEEF, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMEGH, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMEIJ, updatedScoreBoard);


        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEAB, 0, 5, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMECD, 10, 2, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEEF, 2, 2, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEGH, 6, 6, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEIJ, 3, 1, updatedScoreBoard);


        List<Game> games = new FootballScoreboard().getSummary(updatedScoreBoard);

        assertArrayEquals(new String[]{TEAM_G, TEAM_H}, new String[]{games.get(0).getHomeTeamName(), games.get(0).getAwayTeamName()});
        assertArrayEquals(new String[]{TEAM_C, TEAM_D}, new String[]{games.get(1).getHomeTeamName(), games.get(1).getAwayTeamName()});
        assertArrayEquals(new String[]{TEAM_A, TEAM_B}, new String[]{games.get(2).getHomeTeamName(), games.get(2).getAwayTeamName()});
        assertArrayEquals(new String[]{TEAM_I, TEAM_J}, new String[]{games.get(3).getHomeTeamName(), games.get(3).getAwayTeamName()});
        assertArrayEquals(new String[]{TEAM_E, TEAM_F}, new String[]{games.get(4).getHomeTeamName(), games.get(4).getAwayTeamName()});
    }

    @Test
    void shouldGetGameSummary_success() throws Exception {
        Map<String, Game> scoreboard = new HashMap();

        Map<String, Game> updatedScoreBoard = new FootballScoreboard().startGame(GAMEAB, scoreboard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMECD, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().startGame(GAMEEF, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().startGame(new Game(TEAM_A, TEAM_F), updatedScoreBoard);


        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEAB, 1, 2, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMECD, 2, 3, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(GAMEEF, 3, 2, updatedScoreBoard);
        updatedScoreBoard = new FootballScoreboard().updateGameScore(new Game(TEAM_A, TEAM_F), 6, 4, updatedScoreBoard);


        List<Game> games = new FootballScoreboard().getSummary(updatedScoreBoard);

        assertArrayEquals(new String[]{games.get(0).getHomeTeamName(), games.get(0).getAwayTeamName()}, new String[]{TEAM_A, TEAM_F});
        assertArrayEquals(new String[]{games.get(1).getHomeTeamName(), games.get(1).getAwayTeamName()}, new String[]{TEAM_C, TEAM_D});
        assertArrayEquals(new String[]{games.get(2).getHomeTeamName(), games.get(2).getAwayTeamName()}, new String[]{TEAM_E, TEAM_F});
        assertArrayEquals(new String[]{games.get(3).getHomeTeamName(), games.get(3).getAwayTeamName()}, new String[]{TEAM_A, TEAM_B});
    }
}
