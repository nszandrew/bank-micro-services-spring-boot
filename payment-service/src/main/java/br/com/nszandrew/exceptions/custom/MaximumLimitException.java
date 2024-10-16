package br.com.nszandrew.exceptions.custom;

public class MaximumLimitException extends RuntimeException{

    public MaximumLimitException(){
        super("Maximum limit reached");
    }

    public MaximumLimitException(String message){
        super(message);
    }
}
