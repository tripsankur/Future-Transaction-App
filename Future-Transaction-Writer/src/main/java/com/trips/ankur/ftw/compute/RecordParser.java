package com.trips.ankur.ftw.compute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.trips.ankur.ftw.config.AppConfig;
import com.trips.ankur.ftw.datamodel.Record;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class RecordParser {

	private static final String uniqueRows=AppConfig.getConfigClass().getUniqueRows();
	private static final String shortKey = AppConfig.getConfigClass().getShortKey();
	private static final String longKey = AppConfig.getConfigClass().getLongKey();
	
	Map<Record,List<Integer>> recordColl = null;
	
	public RecordParser() {
		recordColl=new HashMap<>();
	}
	
	public Collection<Record> getParsedRecords() { return recordColl.keySet(); }
	
	
	public void parseInputRecord(Record rs){
		String[] uniqR = uniqueRows.split("\\|");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(String key : uniqR) {
			map.put(key, rs.getValue(key));
		}
		addParsedRecordInList(new Record(map), Integer.parseInt((String) rs.getValue(longKey)), Integer.parseInt((String) rs.getValue(shortKey)));
		
	}
	
	private void addParsedRecordInList(Record record, int longQ, int shortQ) {
		if(recordColl.containsKey(record)){
			recordColl.get(record).add((longQ-shortQ));
			//recordColl.put(record, i);
		}else {
			Integer i =  (longQ-shortQ);
			List<Integer> newList = new ArrayList<>();
			newList.add(i);
			recordColl.put(record, newList);
		}
	}
	
	public Integer totalTransactionAmount(List<Integer> amountList) {
		return amountList.stream().mapToInt(Integer::intValue).sum();
	}
	
	

}
