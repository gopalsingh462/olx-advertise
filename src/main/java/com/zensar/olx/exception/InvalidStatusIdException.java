package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidStatusIdException extends RuntimeException{
	private String message;

	public InvalidStatusIdException(String message) {
		super();
		this.message = message;
	}

	public InvalidStatusIdException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid status ID " + this.message;
	}
}
