package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidRecordNumberException extends RuntimeException{
	private String message;

	public InvalidRecordNumberException(String message) {
		super();
		this.message = message;
	}

	public InvalidRecordNumberException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid record number " + this.message;
	}
}
