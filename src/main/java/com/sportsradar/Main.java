package com.sportsradar;

import com.sportsradar.exception.GameAlreadyStartedException;
import com.sportsradar.exception.GameSameTeamsException;
import com.sportsradar.exception.InvalidInputException;
import com.sportsradar.game.Game;
import com.sportsradar.scoreboard.FootballScoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = "";
        String homeTeamName = "";
        String awayTeamName = "";
        Map<String, Game> scoreboard = new HashMap();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Worldcup Scoreboard");
        do {
            System.out.println("Choose one operation");
            System.out.println("(1) Start a game");
            System.out.println("(2) Finish a game");
            System.out.println("(3) Update score");
            System.out.println("(4). Display summary");
            System.out.println("(5). Exit application");
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
                        scoreboard = new FootballScoreboard().startGame(Optional.of(homeTeamName), Optional.of(awayTeamName), scoreboard);
                        System.out.println("Game started with score 0:0");
                    } catch (GameAlreadyStartedException e) {
                        System.out.println(e.getMessage());
                    } catch (GameSameTeamsException e) {
                        System.out.println(e.getMessage());
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2": //TODO
                    break;
                case "3": //TODO
                    break;
                case "4": //TODO
                default:
                    option = "5";
            }
        } while (!option.equals("5"));

    }
}