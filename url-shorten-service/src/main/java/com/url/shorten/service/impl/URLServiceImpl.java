package com.url.shorten.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.url.shorten.convertor.URLConvertor;
import com.url.shorten.entity.JUrl;
import com.url.shorten.exception.NoURLFoundException;
import com.url.shorten.repository.URLRepository;
import com.url.shorten.service.URLService;

/**
 * @author Jaydip
 *
 */
@Service
public class URLServiceImpl implements URLService {

	@Autowired
	private URLRepository urlRepository;
	
	@Autowired
	private URLConvertor urlConvertor;
	
	@Override
	@Transactional
    public String saveAndConvert(String longUrl) {
        
		JUrl jUrlEntity = urlRepository.findByLongUrl(longUrl).orElse(null);
		
		if(null == jUrlEntity) {
			JUrl jUrl = new JUrl();
	    	jUrl.setLongUrl(longUrl);
	    	jUrlEntity = urlRepository.save(jUrl);	
		}
        return urlConvertor.convertToShorten(jUrlEntity.getId());
    }
    
	@Override
    public String getUrl(String shortUrl) throws NoURLFoundException {
        
    	long id = urlConvertor.convertToFullURLId(shortUrl);
    	JUrl entity = urlRepository.findById(id).orElseThrow(() -> 
        	new NoURLFoundException("There is no entity with " + shortUrl));
        return entity.getLongUrl();
    }
}
