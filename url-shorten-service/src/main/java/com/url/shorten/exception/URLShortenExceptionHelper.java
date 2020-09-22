package com.url.shorten.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class URLShortenExceptionHelper {

    @ExceptionHandler(value = { NoURLFoundException.class } )
    public ResponseEntity<Object> handleNoURLFoundException(NoURLFoundException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.NO_CONTENT);
		body.put("message", ex.getMessage());
        return new ResponseEntity<>(body,HttpStatus.NO_CONTENT);
    }
}
