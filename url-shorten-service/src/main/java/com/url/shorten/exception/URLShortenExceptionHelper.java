package com.url.shorten.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class URLShortenExceptionHelper extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { NoURLFoundException.class } )
    public ResponseEntity<Object> handleNoURLFoundException(NoURLFoundException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.NO_CONTENT);
		body.put("message", ex.getMessage());
        return new ResponseEntity<>(body,HttpStatus.NO_CONTENT);
    }
    
	@Override
	public final ResponseEntity<Object> handleMethodArgumentNotValid(
			org.springframework.web.bind.MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		List<String> errors = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			errors.add(error.getDefaultMessage());
		}
		body.put("errors", errors);
		return new ResponseEntity<>(body, headers, status);
	}
}
