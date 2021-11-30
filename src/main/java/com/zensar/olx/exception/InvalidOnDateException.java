package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidOnDateException extends RuntimeException{
	private String message;

	public InvalidOnDateException(String message) {
		super();
		this.message = message;
	}

	public InvalidOnDateException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid on date " + this.message;
	}
}
