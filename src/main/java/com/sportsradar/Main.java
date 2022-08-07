package com.sportsradar;

import com.sportsradar.exception.*;
import com.sportsradar.game.Game;
import com.sportsradar.scoreboard.FootballScoreboard;

import java.util.*;

public class Main {
    public static void main(String[] args) throws FinishGameException, InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, GameScoreException {
        String option = "";
        String homeTeamName = "";
        String awayTeamName = "";
        Map<String, Game> scoreboard = new LinkedHashMap();
        int gameCounter = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Worldcup Scoreboard");
        do {
            System.out.println("Choose one operation");
            System.out.println("(1) Start a game");
            System.out.println("(2) Finish a game");
            System.out.println("(3) Update score");
            System.out.println("(4) Display summary");
            System.out.println("(5) Exit application");
            option = scanner.nextLine();
            switch (option) {
                case "1":
                    System.out.println("Enter home team name");
                    homeTeamName = scanner.nextLine();
                    while (homeTeamName.isEmpty()) {
                        System.out.println("Empty input. Please enter home team name");
                        homeTeamName = scanner.nextLine();
                    }
                    System.out.println("Enter away team name");
                    awayTeamName = scanner.nextLine();
                    while (awayTeamName.isEmpty()) {
                        System.out.println("Empty input. Please enter home team name");
                        awayTeamName = scanner.nextLine();
                    }
                    try {
                        Game footballGame = new Game(homeTeamName, awayTeamName);
                        footballGame.setGameNumber(++gameCounter);
                        scoreboard = new FootballScoreboard().startGame(footballGame, scoreboard);
                        System.out.println("Game started with score 0:0");
                    } catch (GameAlreadyStartedException e) {
                        System.out.println(e.getMessage());
                    } catch (GameSameTeamsException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("Enter home team name");
                    homeTeamName = scanner.nextLine();
                    while (homeTeamName.isEmpty()) {
                        System.out.println("Empty input. Please enter home team name");
                        homeTeamName = scanner.nextLine();
                    }
                    System.out.println("Enter away team name");
                    awayTeamName = scanner.nextLine();
                    while (awayTeamName.isEmpty()) {
                        System.out.println("Empty input. Please enter home team name");
                        awayTeamName = scanner.nextLine();
                    }
                    if (homeTeamName.equalsIgnoreCase(awayTeamName)) {
                        System.out.println("Home team and away team cannot be same");
                        option = "2";
                        break;
                    }
                    try {
                        scoreboard = new FootballScoreboard().finishGame(new Game(homeTeamName, awayTeamName), scoreboard);
                        System.out.println(String.format("Game between %s and %s marked finished successfully", homeTeamName, awayTeamName));
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    } catch (GameAlreadyStartedException e) {
                        System.out.println(e.getMessage());
                    } catch (GameSameTeamsException e) {
                        System.out.println(e.getMessage());
                    } catch (FinishGameException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    Map<Integer, String> gameIds = new HashMap<>();
                    Game game = null;
                    String[] enteredScores = new String[2];
                    boolean goodInput = false;
                    int chosenGameOption = 0;
                    int index = 1;
                    for (Map.Entry<String, Game> entry : scoreboard.entrySet()) {
                        System.out.println(String.format("(%d) %s vs %s", index, entry.getValue().getHomeTeamName().toUpperCase(),
                                entry.getValue().getAwayTeamName().toUpperCase()));
                        gameIds.put(index, entry.getKey());
                        index++;
                    }
                    System.out.println("Choose a game to update score : ");
                    do {
                        option = scanner.nextLine();
                        if (option.isEmpty()) {
                            System.out.println("Invalid input. Choose a valid game.");
                            continue;
                        }
                        try {
                            chosenGameOption = Integer.parseInt(option);
                        } catch (NumberFormatException exception) {
                            System.out.println("Choose a valid game option.");
                            continue;
                        }
                        if (chosenGameOption < 0 && chosenGameOption > gameIds.size()) {
                            System.out.println("Choose a valid game option.");
                            continue;
                        }
                        goodInput = true;

                    } while (goodInput == false);

                    //Enter score
                    boolean validScoreInput = false;
                    do {
                        validScoreInput = true;
                        game = scoreboard.get(gameIds.get(chosenGameOption));
                        System.out.println(String.format("Enter scores in the format: [%s's score]':[%s's score]",
                                game.getHomeTeamName().toUpperCase(), game.getAwayTeamName().toUpperCase()));
                        option = scanner.nextLine();
                        if (!option.isEmpty() && option.matches("\\d+:\\d+")) {
                            enteredScores = option.split(":");
                            for (String score : enteredScores) {
                                int teamScore = -1;
                                try {
                                    teamScore = Integer.parseInt(score.trim());
                                    if (teamScore < 0) {
                                        validScoreInput = false;
                                        System.out.println(" Invalid score entered.");
                                        break;
                                    }
                                } catch (NumberFormatException ex) {
                                    validScoreInput = false;
                                    System.out.println(" Invalid score entered.");
                                }
                            }
                        } else {
                            validScoreInput = false;
                        }
                    } while (validScoreInput == false);

                    final String gameId = gameIds.get(chosenGameOption);
                    scoreboard = new FootballScoreboard().updateGameScore(gameId,
                            Integer.parseInt(enteredScores[0].trim()), Integer.parseInt(enteredScores[1].trim()), scoreboard);

                    System.out.println(String.format("Updated score for %s vs %s game is %d:%d",
                            scoreboard.get(gameId).getHomeTeamName().toUpperCase(), scoreboard.get(gameId).getAwayTeamName().toUpperCase(),
                            scoreboard.get(gameId).getHomeTeamScore(), scoreboard.get(gameId).getAwayTeamScore()));
                    break;
                case "4":
                    System.out.println("Displaying summary");
                    List<Game> orderedGames = new FootballScoreboard().getSummary(scoreboard);
                    orderedGames.forEach((footballGame -> System.out.println(String.format("%s %d - %s %d", footballGame.getHomeTeamName()
                            , footballGame.getHomeTeamScore(), footballGame.getAwayTeamName(), footballGame.getAwayTeamScore()))));
                    break;
                default:
                    option = "5";
            }
        } while (!option.equals("5"));

    }
}