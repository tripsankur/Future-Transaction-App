package com.trips.ankur.ftw.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppConfigTest {

	
	
	@Test
	public void getInstanceTest() {
		assertNotNull(AppConfig.getConfigClass());
	}
	
	@Test
	public void validateConfig() {
		assertEquals("QUANTITY LONG", (AppConfig.getConfigClass()).getLongKey());
		assertEquals("QUANTITY SHORT", (AppConfig.getConfigClass()).getShortKey());
		assertFalse(AppConfig.getConfigClass().getTrimSpace());
		//Rest will be similar
	}


}
