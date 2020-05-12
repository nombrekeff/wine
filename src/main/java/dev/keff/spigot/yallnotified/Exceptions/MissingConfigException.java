package dev.keff.spigot.yallnotified.Exceptions;

public class MissingConfigException extends Exception{
    private static final long serialVersionUID = 1L;

    public MissingConfigException(String message) {
        super(message);
    }
    
}