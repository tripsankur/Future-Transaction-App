package com.trips.ankur.ftw.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConfig {
	private String metadata;
	private String Fixed_Length;
	private String outputStructure;
	private String header;
	private String shortKey;
	private String longKey;
	private String uniqueRows;
	private Boolean trimSpace;
	private AppConfig(){
		//System.out.println("test inside");
		String value=System.getProperty("config");
		String resource_dir = System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties";
		

		try {
			File tempFile = new File(resource_dir);
			boolean exists = tempFile.exists();
			
			InputStream input = new FileInputStream(value == null ? (exists ? resource_dir : ".\\config.properties") : value);
	        Properties prop = new Properties();

	        prop.load(input);
	        this.metadata=prop.getProperty("Column_Metadata");
	        this.Fixed_Length=prop.getProperty("Fixed_Length");
	        this.outputStructure=prop.getProperty("Output_Structure");
	        this.trimSpace=Boolean.parseBoolean(prop.getProperty("Trim_Spaces"));
	        this.header=prop.getProperty("Header");
	        this.shortKey=prop.getProperty("Short_Key");
	        this.longKey=prop.getProperty("Long_Key");
	        this.uniqueRows=prop.getProperty("Unique_Row");

	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }

	}
	private static class SingletonHolder {
		private static final AppConfig instance = new AppConfig();
		
	}
	 public static AppConfig getConfigClass() {
		 return SingletonHolder.instance;
	}
    

}
