package com.url.shorten.convertor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

public class URLConvertorTest {

    @InjectMocks
	private URLConvertor urlConvertor;

    private String base62String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    
    
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	ReflectionTestUtils.setField(urlConvertor, "base62String", base62String);
    	ReflectionTestUtils.setField(urlConvertor, "base62Characters", base62String.toCharArray());
    	ReflectionTestUtils.setField(urlConvertor, "base62length", base62String.toCharArray().length);
    }
    
    @Test
    @DisplayName("Test convertToShorten() with 0")
    public void testConvertToShorten() {
    	
    	String actualResponse = urlConvertor.convertToShorten(0);
    	assertNotNull(actualResponse);
    	assertEquals("a", actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToShorten() with less than 62")
    public void testConvertToShortenWithLessThan62() {
    	
    	String actualResponse = urlConvertor.convertToShorten(26);
    	assertNotNull(actualResponse);
    	assertEquals("A", actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToShorten() with more than 62")
    public void testConvertToShortenWithMoreThan62() {
    	
    	String actualResponse = urlConvertor.convertToShorten(1000);
    	assertNotNull(actualResponse);
    	assertEquals("qi", actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToFullURLId() with a")
    public void test1ConvertToFullURLId() {
    	
    	long actualResponse = urlConvertor.convertToFullURLId("a");
    	assertEquals(0L, actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToFullURLId() with A")
    public void test2ConvertToFullURLId() {
    	
    	long actualResponse = urlConvertor.convertToFullURLId("A");
    	assertEquals(26L, actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToFullURLId() with qi")
    public void test3ConvertToFullURLId() {
    	
    	long actualResponse = urlConvertor.convertToFullURLId("qi");
    	assertEquals(1000L, actualResponse);
    }
    
    @Test
    @DisplayName("Test convertToFullURLId() with random string")
    public void test4ConvertToFullURLId() {
    	
    	long actualResponse = urlConvertor.convertToFullURLId("ABi123");
    	assertTrue(actualResponse > 0);
    }
}
