package com.gojek.parkinglot;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotTest {

	@Test
	public void testParkingOrder() {
		ParkingLot parkingLot = new ParkingLot(2);
		int slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertTrue(slot == 1);
		slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertTrue(slot == 2);
	}

	@Test
	public void testParkingFull() {
		ParkingLot parkingLot = new ParkingLot(2);
		int slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertTrue(slot == 1);
		slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertTrue(slot == 2);
		slot = parkingLot.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		Assert.assertTrue(slot == -1);
	}

	@Test
	public void testParkingDuplicateCar() {
		ParkingLot parkingLot = new ParkingLot(2);
		int slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertTrue(slot == 1);
		slot = parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertTrue(slot == 1);
	}

	@Test
	public void testParkingAfterUnparking() {
		ParkingLot parkingLot = new ParkingLot(5);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		int slotNumber = parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLot.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		parkingLot.unpark(slotNumber);
		int newSlotNumber = parkingLot.park(VehicleType.CAR, "KA-01-HH-2701", "Blue");
		Assert.assertTrue(slotNumber == newSlotNumber);
	}

	@Test
	public void testStatus() {
		ParkingLot parkingLot = new ParkingLot(5);
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 0);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 3);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 3);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 4);
	}

	@Test
	public void testUnpark() {
		ParkingLot parkingLot = new ParkingLot(5);
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 0);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		int slotNumber = parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 3);
		boolean success = parkingLot.unpark(slotNumber);
		Assert.assertTrue(success);
		Assert.assertTrue(parkingLot.getAllParkedSlots().size() == 2);
		success = parkingLot.unpark(slotNumber);
		Assert.assertFalse(success);
		Optional<Slot> optional = parkingLot.getAllParkedSlots().stream().filter((s) -> {
			return s.getParkedVehicle().getRegNumber() == "KA-01-HH-9999";
		}).findAny();
		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void testGetRegNumbersOfColour() {
		ParkingLot parkingLot = new ParkingLot(5);
		parkingLot.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLot.park(VehicleType.CAR, "KA-01-HH-2701", "White");
		parkingLot.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		List<String> regNumbers = parkingLot.getRegNumbersForColour("White");
		Assert.assertTrue(regNumbers.size() == 3);
		regNumbers = parkingLot.getRegNumbersForColour("Blue");
		Assert.assertTrue(regNumbers.size() == 0);
	}
}
