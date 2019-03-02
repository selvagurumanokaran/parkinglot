package com.gojek.parkinglot;

import org.junit.Assert;
import org.junit.Test;

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

}
