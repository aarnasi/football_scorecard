package com.sportsradar.exception;

/**
 * Handles exception in case of an event where a game is expected to start which is already running.
 */
public final class GameAlreadyStartedException extends Exception{
    public GameAlreadyStartedException(){
        super("Game already started.");
    }
}
