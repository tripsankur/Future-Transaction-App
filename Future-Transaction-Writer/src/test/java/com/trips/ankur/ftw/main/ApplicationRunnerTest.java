package com.trips.ankur.ftw.main;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.hamcrest.core.IsInstanceOf;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import com.trips.ankur.ftw.exceptions.NotEnoughArgumentException;
import com.trips.ankur.ftw.main.ApplicationRunner;

public class ApplicationRunnerTest {

	private final static String OUTPUTFILE= "outputfile.csv";


	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@AfterClass
	public static void deleteOutputFile() throws IOException {

		if(Files.exists(Paths.get(OUTPUTFILE)))
			Files.delete(Paths.get(OUTPUTFILE));
	}

	@Test
	public void testMain_NotEnoughArguments() throws Throwable {
		String[]  args = new String[] {};

		//Not Enough Arguments
		expectedEx.expectCause(IsInstanceOf.<Throwable>instanceOf(NotEnoughArgumentException.class));
		AbstractMainTests.executeMain(ApplicationRunner.class, args);
	}

	
	@Test
	public void testMain() throws Throwable {
		String[]  args = new String[2];

		//File metadata = new File(Thread.currentThread().getContextClassLoader().getResource("metadata").toURI());
		File dataFile = new File(Thread.currentThread().getContextClassLoader().getResource("input").toURI());


		args[0]=dataFile.getAbsolutePath();
		args[1]=OUTPUTFILE;


		String[]  result=AbstractMainTests.executeMain(ApplicationRunner.class, args);
		for(String str : result) {
			System.out.println(str);
		}
		assertThat(Files.exists(Paths.get(OUTPUTFILE)), is(true));
	}

}
