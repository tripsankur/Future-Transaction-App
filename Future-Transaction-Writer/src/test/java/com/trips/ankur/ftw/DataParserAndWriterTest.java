package com.trips.ankur.ftw;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.trips.ankur.ftw.data.DataParserAndWriter;
import com.trips.ankur.ftw.datamodel.Column;
import com.trips.ankur.ftw.datamodel.MetaData;
import com.trips.ankur.ftw.datamodel.Record;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * 
 * Test class
 * 
 * @author tripaank
 *
 */

public class DataParserAndWriterTest {

	private DataParserAndWriter parser;
	private final File metadata = new File(
			Thread.currentThread().getContextClassLoader().getResource("metadata").toURI());
	private final File dataFile = new File(
			Thread.currentThread().getContextClassLoader().getResource("input").toURI());

	private static final String OUTPUTFILE= "outputfile.csv";
	
	public DataParserAndWriterTest() throws URISyntaxException {
	}

	@Before
	public void setUp() throws Exception {

	}
	@AfterClass
	public static void deleteOutputFile() throws IOException {
		

		if(Files.exists(Paths.get(OUTPUTFILE)))
			Files.delete(Paths.get(OUTPUTFILE));
	}

	@Test
	public void intilization_Test() {
		parser = new DataParserAndWriter(metadata, dataFile);
		assertNotNull(parser);
	}

	@Test
	public void testMetadataFile() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile);

		assertThat(parser, notNullValue());

		// Act
		MetaData md = parser.parseMetaData();
		Column[] columns = md.getColumns();

		// Assertions
		assertThat(md, notNullValue());
		assertThat(columns, notNullValue());
		assertThat(columns.length, is(11));

	}


	@Test public void testParseAndWriteCsvFile() throws Exception {
		// Arrange
		parser = new DataParserAndWriter(metadata, dataFile); assertThat(parser,
				notNullValue());

		// Act 
		MetaData md = parser.parseMetaData();
		parser.parseDataAndWriteOutput(md, OUTPUTFILE, false);

		//Assertions 
		assertThat(Files.exists(Paths.get(OUTPUTFILE)), is(true));
	}
	
	
	@Test
	public void test_SetRecords() throws IOException {
		parser = new DataParserAndWriter(metadata, dataFile);
		MetaData md = parser.parseMetaData();
		Column[] cl = md.getColumns();
		String lineRecord="315CL  432100020201SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000430DUSD000000000000DJPY201008200012530     688158000092450000000             O";
		
		Record record= parser.setRecord(cl, lineRecord);
		assertNotNull(record);
		//Elements in the record
		assertEquals(11, record.getEntries().size());
		
	}
	

	
	
}