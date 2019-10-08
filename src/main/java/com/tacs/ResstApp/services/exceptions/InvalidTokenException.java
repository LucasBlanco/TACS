package com.tacs.ResstApp.services.exceptions;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = -1325375880704868853L;

	public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
