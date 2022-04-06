package com.ajblima.dscatalog.resource.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ajblima.dscatalog.services.exceptions.DataBaseException;
import com.ajblima.dscatalog.services.exceptions.ResourceNotFoundException;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandartError err = new StandartError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandartError> entityNotFound(DataBaseException e, HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandartError err = new StandartError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Database Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationErro> validation(MethodArgumentNotValidException e, HttpServletRequest request)
	{
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		ValidationErro err = new ValidationErro();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setErro("Validation Exception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		for (FieldError f : e.getBindingResult().getFieldErrors()) {
			err.addErro(f.getField(), f.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
	}
}
