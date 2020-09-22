package com.url.shorten.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.url.shorten.convertor.URLConvertor;
import com.url.shorten.entity.JUrl;
import com.url.shorten.exception.NoURLFoundException;
import com.url.shorten.repository.URLRepository;
import com.url.shorten.service.impl.URLServiceImpl;

public class URLServiceImplTest {

    @Mock
    private URLRepository urlRepository;
    
    @Mock
	private URLConvertor urlConvertor;
    
    @InjectMocks
    private URLServiceImpl urlServiceImpl;
    
    
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    }
    
    @Test
    @DisplayName("Test saveAndConvert() with save")
    public void testSaveAndConvert() {
    	
		JUrl expectedJUrl = new JUrl();
		expectedJUrl.setId(1L);
		expectedJUrl.setLongUrl("http://google.com");
		
		String expectedResponse = "abAb12";
		
    	when(urlConvertor.convertToShorten(Mockito.anyLong())).thenReturn(expectedResponse);
    	when(urlRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.empty());
    	when(urlRepository.save(Mockito.any())).thenReturn(expectedJUrl);

    	String actualResponse = urlServiceImpl.saveAndConvert(expectedJUrl.getLongUrl());
    	
    	assertNotNull(actualResponse);
    	assertEquals(expectedResponse, actualResponse);
    	verify(urlRepository, times(1)).findByLongUrl(Mockito.anyString());
    	verify(urlRepository, times(1)).save(Mockito.any());
    }
    
    @Test
    @DisplayName("Test saveAndConvert() if url already exist")
    public void testSaveAndConvertWithURLExist() {
    	
		JUrl expectedJUrl = new JUrl();
		expectedJUrl.setId(1L);
		expectedJUrl.setLongUrl("http://google.com");
		
		String expectedResponse = "abAb12";
		
    	when(urlConvertor.convertToShorten(Mockito.anyLong())).thenReturn(expectedResponse);
    	when(urlRepository.findByLongUrl(Mockito.anyString())).thenReturn(Optional.of(expectedJUrl));

    	String actualResponse = urlServiceImpl.saveAndConvert(expectedJUrl.getLongUrl());
    	
    	assertNotNull(actualResponse);
    	assertEquals(expectedResponse, actualResponse);
    	verify(urlRepository, times(1)).findByLongUrl(Mockito.anyString());
    	verify(urlRepository, never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("Test getUrl()")
    public void testGetUrl() throws Exception {
    	
    	long expectedId = 101;
		JUrl expectedJUrl = new JUrl();
		expectedJUrl.setId(1L);
		expectedJUrl.setLongUrl("http://google.com");

    	when(urlConvertor.convertToFullURLId(Mockito.anyString())).thenReturn(expectedId);
    	when(urlRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expectedJUrl));
    	
    	String actualResponse = urlServiceImpl.getUrl("1abEF");

    	assertNotNull(actualResponse);
    	assertEquals(expectedJUrl.getLongUrl(), actualResponse);
    }
    
    @Test
    @DisplayName("Test getUrl() with Exception")
    public void testGetUrlWithEx() throws Exception {
    	
    	when(urlRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
    	Assertions.assertThrows(NoURLFoundException.class, () -> {
    		urlServiceImpl.getUrl("1abEF");
    	});
    }
}
