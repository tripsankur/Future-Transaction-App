package com.trips.ankur.ftw.main;

import com.trips.ankur.ftw.config.AppConfig;
import com.trips.ankur.ftw.data.DataParserAndWriter;
import com.trips.ankur.ftw.datamodel.MetaData;
import com.trips.ankur.ftw.exceptions.NotEnoughArgumentException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class. It starts the Application.
 * 
 * @author tripaank
 *
 */
public class ApplicationRunner {

	private static String metaDataFile;
	private static String dataFile;
	private static DataParserAndWriter parser;
	private static String outPutFileName;
	private static boolean processWithError = false;
	private static final Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

	/**
	 * Main Method. arg1 - Metadata File Path arg2 - Data File path arg3 - Output
	 * File Path
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("[Main] Application started. - ");

		if (args.length < 2) {
			logger.error("Not Enough Arguments passed...");
			logger.error("Usage Instructions :");
			logger.error("Please provide 2 arugments as below.");
			logger.error("Argument 1: Data File Complete Path.");
			logger.error("Argument 2: Output File Complete Path.");
			throw new NotEnoughArgumentException(
					"Not Enough Arguments Passed to the application.");
		}

		validateArguements(args);
		//
		
		metaDataFile=AppConfig.getConfigClass().getMetadata();
		try {
			parser = new DataParserAndWriter(FileUtils.getFile(metaDataFile), FileUtils.getFile(dataFile));
			MetaData metaData = parser.parseMetaData();
			parser.parseDataAndWriteOutput(metaData, outPutFileName, processWithError);
			logger.info("[Main] CSV File Written to : " + outPutFileName);

		} catch (Exception e) {
			logger.error("Exception Occured: ", e);
			e.printStackTrace();
		}
	}

	/**
	 * This method Validates the arguments.
	 * 
	 * @param args
	 */
	private static void validateArguements(String[] args) {
		// metaDataFile = StringUtils.defaultString(args[0]);
		
		dataFile = StringUtils.defaultString(args[0]);
		outPutFileName = StringUtils.defaultString(args[1]);
		if (args.length == 2)
			// processWithError = StringUtils.defaultString(args[3]).equals("true") ? true :
			// false;
			processWithError = false;
		if (StringUtils.isAnyBlank( dataFile, outPutFileName)) {
			logger.error("[validateArguements] All 2 manadatory parameters must be specified.");
			logger.warn("[validateArguements] dataFile = " + dataFile);
			logger.warn("[validateArguements] outPutFileName = " + outPutFileName);
			//logger.warn("[validateArguements] processWithError = " + processWithError);
			throw new NotEnoughArgumentException(
					"[validateArguements] Empty Arguments passed - List of Arguments are : " + args);
		}
	}
}
