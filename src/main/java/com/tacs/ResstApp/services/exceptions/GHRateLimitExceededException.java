package com.tacs.ResstApp.services.exceptions;

import java.io.IOException;

public class GHRateLimitExceededException extends IOException {

	private static final long serialVersionUID = 4811414223518264645L;

	public GHRateLimitExceededException(Long minutes) {
        super("API rate limit exceeded. Try again in " + minutes + " minutes.");
    }

    public GHRateLimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
