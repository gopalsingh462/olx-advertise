package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidDateFormatException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidDateFormatException(String message) {
		super();
		this.message = message;
	}

	public InvalidDateFormatException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid date format " + this.message;
	}
}
