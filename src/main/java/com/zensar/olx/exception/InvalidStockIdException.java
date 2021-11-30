package com.zensar.olx.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidStockIdException extends RuntimeException {
	private String message;

	public InvalidStockIdException(String message) {
		super();
		this.message = message;
	}

	public InvalidStockIdException() {

		this.message = "";
	}

	@Override
	public String toString() {
		return "Invalid stock ID " + this.message;
	}

}
