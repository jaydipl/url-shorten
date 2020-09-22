package com.url.shorten.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.url.shorten.dto.URLShortenRequest;
import com.url.shorten.exception.NoURLFoundException;
import com.url.shorten.service.URLService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * A controller class handles request for URL shorting
 * 
 * @author Jaydip
 *
 */
@RestController
@RequestMapping(path = "api")
public class URLShortenController {

	@Autowired
	private URLService urlService;
	
	private final String SHORTEN_URL = "shorten-url";
	
	/**
	 * @param urlShorteningRequest
	 * @return
	 */
	@Operation(description = "Convert long url to shorten")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "SUCCESS"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "503", description = "Something went wrong"),
	})
	@PostMapping("shorten")
	public Map<String,String> generateShortening(@RequestBody @Valid URLShortenRequest urlShorteningRequest) {
	
		String shortenUrl = urlService.saveAndConvert(urlShorteningRequest.getLongUrl());
		Map<String, String> response = new HashMap<>();
		response.put(SHORTEN_URL, shortenUrl);
		return response;
	}
	
	/**
	 * @param shortenUrl
	 * @return
	 * @throws NoURLFoundException 
	 */
	@Operation(description = "Get full URL from shorten id")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "SUCCESS"),
			@ApiResponse(responseCode = "404", description = "NOT FOUND"),
			@ApiResponse(responseCode = "503", description = "Something went wrong"), 
	})
	@GetMapping("getFullUrl/{shortenUrl}")
	public URLShortenRequest getFullUrl(@PathVariable String shortenUrl) throws NoURLFoundException {
		
		String longUrl = urlService.getUrl(shortenUrl);
		URLShortenRequest urlShortenRequest = new URLShortenRequest();
		urlShortenRequest.setLongUrl(longUrl);
		return urlShortenRequest;
	}
}