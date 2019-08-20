package com.trips.ankur.ftw.data;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trips.ankur.ftw.compute.RecordParser;
import com.trips.ankur.ftw.config.AppConfig;
import com.trips.ankur.ftw.datamodel.Column;
import com.trips.ankur.ftw.datamodel.MetaData;
import com.trips.ankur.ftw.datamodel.Record;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 * This class performs the Parsing of data and it writes the data in the output
 * file.
 * 
 * @author tripaank
 *
 */

@Getter
@Setter
public class DataParserAndWriter {
	private static final Logger logger = LoggerFactory.getLogger(DataParserAndWriter.class);

	private File metadataFile;
	private File dataFile;
	// private SimpleDateFormat sdf = new SimpleDateFormat();

	public DataParserAndWriter(File metadataFile, File dataFile) {
		this.metadataFile = metadataFile;
		this.dataFile = dataFile;
	}

	/**
	 * This is to pars the metadata.
	 * 
	 * @return
	 * @throws IOException
	 */

	public MetaData parseMetaData() throws IOException {
		logger.info("[parseMetaData] Parsing the metadata file.");
		MetaData metaData = new MetaData();
		Reader r = new FileReader(metadataFile);
		Object[] records = IteratorUtils.toArray(CSVFormat.RFC4180.parse(IOUtils.toBufferedReader(r)).iterator());
		Column[] columns = new Column[records.length];

		for (int i = 0; i < records.length; i++) {
			CSVRecord record = (CSVRecord) records[i];
			String columnName = record.get(0);
			int startIndex = Integer.parseInt(record.get(1));
			int endIndex = Integer.parseInt(record.get(2));
			String columnType = record.get(3);
			String format = record.get(4);
			Column c = new Column(columnName, startIndex,endIndex, columnType, format);
			columns[i] = c;
		}
		metaData.setColumns(columns);
		logger.info("[parseMetaData] Parsing the metadata file completes.");
		logger.debug("[parseMetaData] Metadata information is : " + metaData.toString());
		return metaData;
	}



	/**
	 * Perform parsing and writing the output data. This method reads the data line
	 * by line, pars the data and writes it in the output file.
	 * 
	 * @param metaData
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	
	public boolean parseDataAndWriteOutput(MetaData metaData, String fileName, boolean writeError) throws Exception {

		logger.info("[parseDataAndWriteOutput] Start Reading the data file for processing.");

		try (LineIterator it = FileUtils.lineIterator(dataFile, StandardCharsets.UTF_8.name())) {
			RecordParser recordParser = new RecordParser();
			Column[] columns = metaData.getColumns();
			int linelenght= Integer.parseInt(AppConfig.getConfigClass().getFixed_Length());
			Iterable<String> iterable = () -> (Iterator<String>) it;
			StreamSupport.stream(iterable.spliterator(), true).forEach(line -> {
				
				//Read the line only if the FixedWidth matches the configuration
				if(line.length() == linelenght) {
					Record rs = setRecord(columns,line);
					recordParser.parseInputRecord(rs);
				}
			});
			//System.out.println(recordParser);
			logger.info("[parseDataAndWriteOutput] Finished Reading the input and parsing the data.");	
			outWriter(recordParser,fileName);
		}

		logger.info("[parseDataAndWriteOutput] End of Parsing the data file and writing output to output file.");
		return true;
	}

	private void outWriter(RecordParser rp, String fileName) throws IOException {
		logger.info("[outWriter] Starting the writer function...");
		File output = new File(fileName);
		String header = AppConfig.getConfigClass().getHeader();
		FileUtils.write(output, format("%s\r\n", header), StandardCharsets.UTF_8);

		String headerConfig=AppConfig.getConfigClass().getOutputStructure();
		Map<Record,List<Integer>> rpMap  = rp.getRecordColl();
		
		
		StringBuffer[] data = new StringBuffer[3];
		for(Record parsedRecord : rp.getParsedRecords()) {
			logger.debug("[outWriter] Parsed Unique Record : "+parsedRecord);
			
			List<Integer> amountList =  rpMap.get(parsedRecord);
			logger.debug("[outWriter] Parsed Unique Record Transaction List : "+amountList);
			
			Integer sum=rp.totalTransactionAmount(amountList);
					
			
			int i=0;
			for(String headerVal : headerConfig.split(",")) {
				
				data[i] = new StringBuffer();
				for(String key : headerVal.split("\\|")) {					
					data[i].append(parsedRecord.getValue(key));
				}
				i++;
			}
			data[2] = new StringBuffer();
			data[2].append(sum+"");
			String record = Arrays.stream(data).collect(Collectors.joining(","));
			//System.out.println(record);
			FileUtils.write(output, format("%s\r\n", record), StandardCharsets.UTF_8, true);
		}
		
	}


	public Record setRecord(Column[] columns, String line) {
		Record record = new Record();
		for (int i = 0; i < columns.length; i++) {
			Column column = columns[i];
			int start = column.getStartIndex()-1;
			int end = column.getEndIndex();
			String name=column.getColumnName();
			if(AppConfig.getConfigClass().getTrimSpace())
				record.setValue(name, substring(line, start, end).trim());
			else
				record.setValue(name, substring(line, start, end));
					
		}
		
		return record;
	}
}
