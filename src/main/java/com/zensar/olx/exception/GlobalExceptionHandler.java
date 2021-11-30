package com.zensar.olx.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(value= {InvalidStockIdException.class})
	public ResponseEntity<Object> handleInvalidStockIdError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidCategoryIdException.class})
	public ResponseEntity<Object> handleInvalidCategoryIdException(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidAuthTokenException.class})
	public ResponseEntity<Object> handleInvalidAuthTokenError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidAdvertiseIdException.class})
	public ResponseEntity<Object> handleInvalidAdvertiseIdError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidOnDateException.class})
	public ResponseEntity<Object> handleInvalidOnDateError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidFromDateException.class})
	public ResponseEntity<Object> handleInvalidFromDateError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidStartIndexException.class})
	public ResponseEntity<Object> handleInvalidStartIndexError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidRecordNumberException.class})
	public ResponseEntity<Object> handleInvalidRecordNumberError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value= {InvalidDateConditionException.class})
	public ResponseEntity<Object> handleInvalidDateConditionError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler(value= {InvalidDateFormatException.class})
	public ResponseEntity<Object> handleInvalidDateFormatError(RuntimeException exception, WebRequest request){
		return handleExceptionInternal(exception, exception.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}
