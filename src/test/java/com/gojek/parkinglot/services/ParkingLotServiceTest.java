package com.gojek.parkinglot.services;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotServiceTest {

	private ByteArrayOutputStream output = new ByteArrayOutputStream();

	@Before
	public void setup() {
		System.setOut(new PrintStream(output));
	}

	@Test
	public void testPark() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 2);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals("Allocated slot number: 1", output.toString().trim());
		output.reset();
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertEquals("Allocated slot number: 2", output.toString().trim());
	}

	@Test
	public void testParkingFull() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 1);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals("Allocated slot number: 1", output.toString().trim());
		output.reset();
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertEquals("Sorry, parking lot is full", output.toString().trim());
	}

	@Test
	public void testParkingSameVehicle() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 2);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals("Allocated slot number: 1", output.toString().trim());
		output.reset();
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals("Allocated slot number: 1", output.toString().trim());
	}

	@Test
	public void printStatus() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		output.reset();
		parkingLotService.printStatus(0);
		String expected = "Slot No.    Registration No    Colour\n1           KA-01-HH-1234      White\n2           KA-01-HH-9999      White\n3           KA-01-BB-0001      Black";
		Assert.assertEquals(expected.trim(), output.toString().trim());
	}

	@Test
	public void testUnparking() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.unpark(0, 2);
		output.reset();
		parkingLotService.printStatus(0);
		String expected = "Slot No.    Registration No    Colour\n1           KA-01-HH-1234      White\n3           KA-01-BB-0001      Black";
		Assert.assertEquals(expected.trim(), output.toString().trim());
	}

	@Test
	public void testParkingAfterUnpark() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.unpark(0, 2);
		output.reset();
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		Assert.assertEquals("Allocated slot number: 2", output.toString().trim());
	}

	@Test
	public void testPrintRegNumbersForColour() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		output.reset();
		parkingLotService.printRegNumbersForColour(0, "Red");
		Assert.assertEquals(output.toString().trim(), "KA-01-HH-9999, KA-01-HH-7777".trim());
		output.reset();
		parkingLotService.printRegNumbersForColour(0, "Black");
		Assert.assertEquals(output.toString().trim(), "KA-01-BB-0001".trim());
		output.reset();
		parkingLotService.printRegNumbersForColour(0, "Blue");
		Assert.assertEquals(output.toString().trim(), "");
	}

	@Test
	public void testPrintSlotNumbersForColour() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		output.reset();
		parkingLotService.printSlotNumbersForColour(0, "Red");
		Assert.assertEquals(output.toString().trim(), "2, 4".trim());
		output.reset();
		parkingLotService.printSlotNumbersForColour(0, "Black");
		Assert.assertEquals(output.toString().trim(), "3".trim());
		output.reset();
		parkingLotService.printSlotNumbersForColour(0, "Blue");
		Assert.assertEquals(output.toString().trim(), "");
	}

	@Test
	public void testPrintSlotForRegNum() {
		ParkingLotService parkingLotService = new ParkingLotService(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		output.reset();
		parkingLotService.printSlotForRegNum(0, "KA-01-HH-1234");
		Assert.assertEquals(output.toString().trim(), "1".trim());
		output.reset();
		parkingLotService.printSlotForRegNum(0, "KA-01-BB-0001");
		Assert.assertEquals(output.toString().trim(), "3".trim());
		output.reset();
		parkingLotService.printSlotForRegNum(0, "Blue");
		Assert.assertEquals(output.toString().trim(), "Not found".trim());
	}

	@After
	public void tearup() {
		System.setOut(System.out);
	}
}
