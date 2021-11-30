package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidDateConditionException extends RuntimeException{
	private String message;

	public InvalidDateConditionException(String message) {
		super();
		this.message = message;
	}

	public InvalidDateConditionException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid date condition " + this.message;
	}
}
