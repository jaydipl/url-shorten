package com.url.shorten.convertor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class is responsible to convert long url to shorten and shorten url to full url respectively
 * @author Jaydip
 *
 */
@Component
public class URLConvertor {

	@Value("${url-shorten-base62-string}")
	private String base62String;
	private char[] base62Characters;
	private int base62length;
	
	@PostConstruct
	public void setValues() {
		if(null != base62String) {
			this.base62Characters = base62String.toCharArray();			
			this.base62length = base62Characters.length;
		}
	}
	
	/**
     * @param id
     * @return
     */
    public String convertToShorten(long id) {
    	    	
        if(id == 0) {
            return String.valueOf(base62Characters[0]);
        }
    
        StringBuilder convertedString = new StringBuilder();
        while (id > 0) {
        	convertedString.append(base62Characters[(int) (id % base62length)]);
            id = id / base62length;
        }

        return convertedString.reverse().toString();
    }
  
    /**
     * @param shortenString
     * @return
     */
    public long convertToFullURLId(String shortenString) {
        
    	char[] shortenChars = shortenString.toCharArray();
        int length = shortenChars.length;

        int id = 0;
        int counter = 1;
        for (int i = 0; i < length; i++) {
        	id += base62String.indexOf(shortenChars[i]) * Math.pow(base62length, length - counter);
            counter++;
        }
        return id;
    }
}
