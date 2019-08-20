package com.trips.ankur.ftw.datamodel;

import static org.junit.Assert.*;

import org.junit.Test;

import com.trips.ankur.ftw.compute.RecordParser;

public class RecordTest {


	@Test
	public void recordInstanceTest() {
		Record instance =new Record();
		assertNotNull(instance);
		assertNotNull(instance.getEntries());
		assertTrue(instance.getNames().isEmpty());

	}
	
	@Test
	public void setRecordTest() {
		Record instance =new Record();
		instance.setValue("test", "testval");
		assertNotNull(instance);
		assertEquals("testval", (instance.getValue("test")));
		assertFalse(instance.getNames().isEmpty());

	}
}
