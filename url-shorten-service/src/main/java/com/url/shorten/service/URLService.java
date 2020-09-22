package com.url.shorten.service;

import com.url.shorten.exception.NoURLFoundException;

public interface URLService {

    public String saveAndConvert(String longUrl);
    
    public String getUrl(String shortUrl) throws NoURLFoundException;
}
