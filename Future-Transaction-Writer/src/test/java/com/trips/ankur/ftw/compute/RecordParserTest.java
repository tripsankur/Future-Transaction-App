package com.trips.ankur.ftw.compute;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


import com.trips.ankur.ftw.config.AppConfig;
import com.trips.ankur.ftw.data.DataParserAndWriter;
import com.trips.ankur.ftw.datamodel.Record;


public class RecordParserTest {


	Record rs;
	Record rs1;
	Record rs2;
	AppConfig appConfig;
	
	@Before
	public void setUp() {
		appConfig = Mockito.mock(AppConfig.class);
		Mockito.when(appConfig.getUniqueRows()).thenReturn("CLIENT TYPE|CLIENT NUMBER|ACCOUNT NUMBER|SUBACCOUNT NUMBER|EXCHANGE CODE|PRODUCT GROUP CODE|SYMBOL|EXPIRATION DATE");
		Mockito.when(appConfig.getShortKey()).thenReturn("QUANTITY SHORT");
		Mockito.when(appConfig.getLongKey()).thenReturn("QUANTITY LONG");
		
		rs = Mockito.mock(Record.class);
		rs1 = Mockito.mock(Record.class);
		rs2 = Mockito.mock(Record.class);
		
		//First Record
		Map<String, String> recordData=new HashMap<>();
		recordData.put("CLIENT TYPE", "Type");
		recordData.put("CLIENT NUMBER", "123");
		recordData.put("ACCOUNT NUMBER", "456");
		recordData.put("SUBACCOUNT NUMBER", "789");
		recordData.put("EXCHANGE CODE", "EX");
		recordData.put("PRODUCT GROUP CODE", "PGC");
		recordData.put("SYMBOL", "TEST");
		recordData.put("EXPIRATION DATE", "20101030");
		
		//Second Record -- Similar to 1
		Map<String, String> recordData1=new HashMap<>();
		recordData1.put("CLIENT TYPE", "Type");
		recordData1.put("CLIENT NUMBER", "123");
		recordData1.put("ACCOUNT NUMBER", "456");
		recordData1.put("SUBACCOUNT NUMBER", "789");
		recordData1.put("EXCHANGE CODE", "EX");
		recordData1.put("PRODUCT GROUP CODE", "PGC");
		recordData1.put("SYMBOL", "TEST");
		recordData1.put("EXPIRATION DATE", "20101030");
		
		//Third Record -- New Uniq Records 
		Map<String, String> recordData2=new HashMap<>();
		recordData2.put("CLIENT TYPE", "newType");
		recordData2.put("CLIENT NUMBER", "999");
		recordData2.put("ACCOUNT NUMBER", "555");
		recordData2.put("SUBACCOUNT NUMBER", "000");
		recordData2.put("EXCHANGE CODE", "XE");
		recordData2.put("PRODUCT GROUP CODE", "CGP");
		recordData2.put("SYMBOL", "WG");
		recordData2.put("EXPIRATION DATE", "20101030");
		
		
		String[] uniqR = appConfig.getUniqueRows().split("\\|");
		for(String u : uniqR) {
			Mockito.when(rs.getValue(u)).thenReturn(recordData.get(u));
			Mockito.when(rs1.getValue(u)).thenReturn(recordData1.get(u));
			Mockito.when(rs2.getValue(u)).thenReturn(recordData2.get(u));
		}
		Mockito.when(rs.getValue(appConfig.getLongKey())).thenReturn("200");
		Mockito.when(rs.getValue(appConfig.getShortKey())).thenReturn("100");
		Mockito.when(rs1.getValue(appConfig.getLongKey())).thenReturn("8");
		Mockito.when(rs1.getValue(appConfig.getShortKey())).thenReturn("2");
		
		Mockito.when(rs2.getValue(appConfig.getLongKey())).thenReturn("5");
		Mockito.when(rs2.getValue(appConfig.getShortKey())).thenReturn("2");
		
	}

	
	@Test
	public void recordParserInstanceTest() {
		RecordParser instance =new RecordParser();
		assertNotNull(instance);
		assertNotNull(instance.getRecordColl());
		assertTrue(instance.getRecordColl().isEmpty());
		assertTrue(instance.getParsedRecords().isEmpty());
		
	}

	
	@Test
	public void parseInputRecordTest_FirstTime() {
		RecordParser instance =new RecordParser();
		//Before
		assertNotNull(instance);
		assertNotNull(instance.getRecordColl());
		assertTrue(instance.getRecordColl().isEmpty());
		assertTrue(instance.getParsedRecords().isEmpty());
		
		//Parse the record
		
		instance.parseInputRecord(rs);
		
		assertFalse(instance.getRecordColl().isEmpty());
		assertFalse(instance.getParsedRecords().isEmpty());

		
		//Validate the Long-Short		
		assertEquals(Integer.valueOf(100),instance.getRecordColl().get(instance.getParsedRecords().iterator().next()).get(0));
	
	}
	
	@Test
	public void parseInputRecordTest_MultipleRecords() {
		RecordParser instance =new RecordParser();
		//Before
		assertNotNull(instance);
		assertNotNull(instance.getRecordColl());
		assertTrue(instance.getRecordColl().isEmpty());
		assertTrue(instance.getParsedRecords().isEmpty());
		
		//Parse the record
		
		instance.parseInputRecord(rs);
		
		assertFalse(instance.getRecordColl().isEmpty());
		assertFalse(instance.getParsedRecords().isEmpty());
		assertEquals(1,instance.getParsedRecords().size());
		
		//Validate the Long-Short		
		assertEquals(Integer.valueOf(100),instance.getRecordColl().get(instance.getParsedRecords().iterator().next()).get(0));
		
		
		instance.parseInputRecord(rs1);
		
		assertEquals(1,instance.getParsedRecords().size());
		assertEquals(1,instance.getRecordColl().size());
		assertEquals(2,instance.getRecordColl().get(instance.getParsedRecords().iterator().next()).size());
		assertEquals(Integer.valueOf(100),instance.getRecordColl().get(instance.getParsedRecords().iterator().next()).get(0));
		assertEquals(Integer.valueOf(6),instance.getRecordColl().get(instance.getParsedRecords().iterator().next()).get(1));
		
		instance.parseInputRecord(rs2);
		
		assertEquals(2,instance.getParsedRecords().size());
		assertEquals(2,instance.getRecordColl().size());
	
	}
	@Test
	public void totalTransactionAmount_test() {
		List<Integer> transactionAmountList=new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		RecordParser instance =new RecordParser();
		assertEquals(Integer.valueOf(15),instance.totalTransactionAmount(transactionAmountList));
		
	}
	
	
}
