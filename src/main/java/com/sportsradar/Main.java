package com.sportsradar;

import com.sportsradar.exception.*;
import com.sportsradar.game.Game;
import com.sportsradar.scoreboard.FootballScoreboard;

import java.util.*;

public class Main {
    public static void main(String[] args) throws FinishGameException, InvalidInputException, GameAlreadyStartedException, GameSameTeamsException, GameScoreException {
        String chosenOperationoption = "";
        String homeTeamName = "";
        String awayTeamName = "";
        Map<String, Game> scoreboard = new HashMap();

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
            chosenOperationoption = scanner.nextLine();
            switch (chosenOperationoption) {
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
                    if (scoreboard.isEmpty()) {
                        System.out.println("No games running.");
                        break;
                    }
                    List<String[]> candidateTeamNamesForFinishAction = new ArrayList();
                    int recordCounterForFinishAction = 1;
                    for (Map.Entry<String, Game> entry : scoreboard.entrySet()) {
                        System.out.println(String.format("(%d) %s vs %s", recordCounterForFinishAction, entry.getValue().getHomeTeamName().toUpperCase(),
                                entry.getValue().getAwayTeamName().toUpperCase()));
                        candidateTeamNamesForFinishAction.add(new String[]{entry.getValue().getHomeTeamName(), entry.getValue().getAwayTeamName()});
                        recordCounterForFinishAction++;
                    }
                    int chosenGame = getChosenGameToFinish(candidateTeamNamesForFinishAction.size());
                    final String[] teamNames = candidateTeamNamesForFinishAction.get(chosenGame - 1);
                    try {
                        scoreboard = new FootballScoreboard().finishGame(new Game(teamNames[0], teamNames[1]), scoreboard);
                        System.out.println(String.format("Game between %s and %s marked finished successfully", homeTeamName, awayTeamName));
                    } catch (FinishGameException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    if (scoreboard.isEmpty()) {
                        System.out.println("No games running.");
                        break;
                    }
                    List<String[]> candidateTeamNamesForUpdateAction = new ArrayList();
                    int recordCounterForUpdateAction = 0;
                    for (Map.Entry<String, Game> entry : scoreboard.entrySet()) {
                        System.out.println(String.format("(%d) %s vs %s", recordCounterForUpdateAction + 1, entry.getValue().getHomeTeamName().toUpperCase(),
                                entry.getValue().getAwayTeamName().toUpperCase()));
                        candidateTeamNamesForUpdateAction.add(new String[]{entry.getValue().getHomeTeamName(), entry.getValue().getAwayTeamName()});
                        recordCounterForUpdateAction++;
                    }
                    System.out.println("Choose a game to update score : ");
                    int chosenGameOptionForUpdate = getChosenGameOptionForUpdate(scoreboard.size());
                    final String[] teamNamesChosenForUpdate = candidateTeamNamesForUpdateAction.get(chosenGameOptionForUpdate - 1);

                    int[] enteredScores = getEnteredScore(teamNamesChosenForUpdate);

                    scoreboard = new FootballScoreboard().updateGameScore(new Game(teamNamesChosenForUpdate[0], teamNamesChosenForUpdate[1]),
                            enteredScores[0], enteredScores[1], scoreboard);

                    System.out.println(String.format("Updated score for %s vs %s game is %d:%d",
                            teamNamesChosenForUpdate[0], teamNamesChosenForUpdate[1],
                            enteredScores[0], enteredScores[1]));
                    break;
                case "4":
                    System.out.println("Displaying summary");
                    List<Game> orderedGames = new FootballScoreboard().getSummary(scoreboard);
                    orderedGames.forEach((footballGame -> System.out.println(String.format("%s %d - %s %d", footballGame.getHomeTeamName()
                            , footballGame.getHomeTeamScore(), footballGame.getAwayTeamName(), footballGame.getAwayTeamScore()))));
                    break;
                default:
                    chosenOperationoption = "5";
            }
        } while (!chosenOperationoption.equals("5"));

    }

    private static int[] getEnteredScore(String[] teamNames) {
        String[] enteredScores;
        boolean validScoreInput = false;
        String scoreEntered;
        Scanner scanner = new Scanner(System.in);
        do {
            validScoreInput = true;
            System.out.println(String.format("Enter scores in the format: [%s's score]':[%s's score]",
                    teamNames[0], teamNames[1]));
            scoreEntered = scanner.nextLine();
            if (!scoreEntered.isEmpty() && scoreEntered.matches("\\d+:\\d+")) {
                enteredScores = scoreEntered.split(":");
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

        String[] scores = scoreEntered.split(":");
        return new int[]{Integer.parseInt(scores[0].trim()), Integer.parseInt(scores[1].trim())};
    }

    private static int getChosenGameOptionForUpdate(int totalGameSize) {
        int chosenGameOption = 0;
        boolean goodInput = false;
        Scanner scanner = new Scanner(System.in);
        String chosenOperationOption;
        do {
            chosenOperationOption = scanner.nextLine();
            if (chosenOperationOption.isEmpty()) {
                System.out.println("Invalid input. Choose a valid game.");
                continue;
            }
            try {
                chosenGameOption = Integer.parseInt(chosenOperationOption);
            } catch (NumberFormatException exception) {
                System.out.println("Choose a valid game option.");
                continue;
            }
            if (chosenGameOption < 0 && chosenGameOption > totalGameSize) {
                System.out.println("Choose a valid game option.");
                continue;
            }
            goodInput = true;

        } while (goodInput == false);
        return chosenGameOption;
    }

    private static int getChosenGameToFinish(int totalGameCount) {
        int chosenGameOption = 0;
        String option;
        boolean goodInput = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a game to finish: ");
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
            if (chosenGameOption < 0 && chosenGameOption > totalGameCount) {
                System.out.println("Choose a valid game option.");
                continue;
            }
            goodInput = true;

        } while (goodInput == false);
        return Integer.parseInt(option);
    }
}