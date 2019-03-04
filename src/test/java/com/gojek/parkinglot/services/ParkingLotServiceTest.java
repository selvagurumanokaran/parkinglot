package com.gojek.parkinglot.services;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.gojek.parkinglot.exceptions.InvalidCommandException;

public class ParkingLotServiceTest {

	@Test
	public void testParkingLotService() throws FileNotFoundException, InvalidCommandException {
		ParkingLotService.main(new String[] { "resources/file_input.txt" });
	}
}
