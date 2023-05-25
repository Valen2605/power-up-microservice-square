package com.pragma.powerup.squaremicroservice.configuration.security.exception;



public class TokenException extends RuntimeException{
    public TokenException() {
        super("A problem with the token has occurred");
    }
}
