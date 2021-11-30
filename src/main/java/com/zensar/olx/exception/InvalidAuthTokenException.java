package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidAuthTokenException extends RuntimeException{
	private String message;

	public InvalidAuthTokenException(String message) {
		super();
		this.message = message;
	}

	public InvalidAuthTokenException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid auth token " + this.message;
	}
}
