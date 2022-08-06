package com.sportsradar.exception;

/**
 * Handles exception in case of game being played between same teams.
 */
public final class GameSameTeamsException extends Exception{
    public GameSameTeamsException(){
        super("Home team and away team cannot be same");
    }
}
