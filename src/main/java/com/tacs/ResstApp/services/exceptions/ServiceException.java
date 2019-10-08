package com.tacs.ResstApp.services.exceptions;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7503571023150785612L;

	public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
