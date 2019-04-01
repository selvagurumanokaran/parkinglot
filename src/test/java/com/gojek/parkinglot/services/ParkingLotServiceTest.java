package com.gojek.parkinglot.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gojek.parkinglot.exceptions.InvalidCommandException;

public class ParkingLotServiceTest {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();

	@Before
	public void setup() {
		System.setOut(new PrintStream(output));
	}

	@Test
	public void testParkingLotService() throws FileNotFoundException, InvalidCommandException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("file_input.txt").getFile());
		ParkingLotService.main(new String[] { file.getAbsolutePath() });
		String expected = String.join("", "Created a parking lot with 6 slots\n", "Allocated slot number: 1\n",
				"Allocated slot number: 2\n", "Allocated slot number: 3\n", "Allocated slot number: 4\n",
				"Allocated slot number: 5\n", "Allocated slot number: 6\n", "Slot number 4 is free\n",
				"Slot No.    Registration No    Colour\n1           KA-01-HH-1234      White\n2           KA-01-HH-9999      White\n3           KA-01-BB-0001      Black\n5           KA-01-HH-2701      Blue\n6           KA-01-HH-3141      Black\n",
				"Allocated slot number: 4\n", "Sorry, parking lot is full\n",
				"KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333\n", "1, 2, 4\n", "6\n", "Not found\n");
		Assert.assertEquals(expected, output.toString());
	}
	
	@Test
	public void testCreateParkingLot() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("file_create.txt").getFile());
	}
}
