package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidFromDateException extends RuntimeException{
	private String message;

	public InvalidFromDateException(String message) {
		super();
		this.message = message;
	}

	public InvalidFromDateException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid fromDate " + this.message;
	}
}
