package dev.keff.spigot.yallnotified.exceptions;

public class MissingConfigException extends Exception{
    private static final long serialVersionUID = 1L;

    public MissingConfigException(String message) {
        super(message);
    }
    
}