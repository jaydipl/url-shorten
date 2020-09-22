package com.url.shorten.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author Jaydip
 *
 */
public class URLShortenRequest {
	
	@NotEmpty(message = "Please provide longUrl")
	private String longUrl;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
}
