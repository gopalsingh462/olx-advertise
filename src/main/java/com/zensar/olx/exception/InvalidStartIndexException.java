package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidStartIndexException extends RuntimeException{
	private String message;

	public InvalidStartIndexException(String message) {
		super();
		this.message = message;
	}

	public InvalidStartIndexException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid start index " + this.message;
	}
}
