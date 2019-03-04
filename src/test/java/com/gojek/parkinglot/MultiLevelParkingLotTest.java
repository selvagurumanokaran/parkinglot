package com.gojek.parkinglot;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.VehicleType;

public class MultiLevelParkingLotTest {

	@Test
	public void testPark() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 2);
		int slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals(slot, 1);
		slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertEquals(2, slot);
	}

	@Test
	public void testParkingFull() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 1);
		int slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals(1, slot);
		slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		Assert.assertEquals(-1, slot);
	}

	@Test
	public void testParkingSameVehicle() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 2);
		int slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals(1, slot);
		slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		Assert.assertEquals(1, slot);
	}

	@Test
	public void printStatus() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		Collection<Slot> slots = parkingLotService.getStatusForLevel(0);
		Assert.assertEquals(slots.size(), 3);
		Iterator<Slot> slotIt = slots.iterator();
		Assert.assertEquals(slotIt.next().getParkedVehicle().getRegNumber(), "KA-01-HH-1234");
		Assert.assertEquals(slotIt.next().getParkedVehicle().getRegNumber(), "KA-01-HH-9999");
		Assert.assertEquals(slotIt.next().getParkedVehicle().getRegNumber(), "KA-01-BB-0001");
	}

	@Test
	public void testUnparking() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.unparkAtLevel(0, 2);
		Collection<Slot> slots = parkingLotService.getStatusForLevel(0);
		Assert.assertEquals(slots.size(), 2);
		Iterator<Slot> slotIt = slots.iterator();
		Assert.assertEquals(slotIt.next().getParkedVehicle().getRegNumber(), "KA-01-HH-1234");
		Assert.assertEquals(slotIt.next().getParkedVehicle().getRegNumber(), "KA-01-BB-0001");
	}

	@Test
	public void testParkingAfterUnpark() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 3);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.unparkAtLevel(0, 2);
		int slot = parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		Assert.assertEquals(2, slot);
	}

	@Test
	public void testPrintRegNumbersForColour() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		Collection<String> regNumbers = parkingLotService.getRegNumbersForColour(0, "Red");
		Assert.assertEquals(regNumbers.size(), 2);
		Iterator<String> regNumIt = regNumbers.iterator();
		Assert.assertEquals(regNumIt.next(), "KA-01-HH-9999");
		Assert.assertEquals(regNumIt.next(), "KA-01-HH-7777");
		regNumbers = parkingLotService.getRegNumbersForColour(0, "Black");
		Assert.assertEquals(regNumbers.size(), 1);
		regNumIt = regNumbers.iterator();
		Assert.assertEquals(regNumIt.next(), "KA-01-BB-0001");
		regNumbers = parkingLotService.getRegNumbersForColour(0, "Blue");
		Assert.assertEquals(regNumbers.size(), 0);
	}

	@Test
	public void testPrintSlotNumbersForColour() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		Collection<Integer> slotNums = parkingLotService.getSlotNumbersForColour(0, "Red");
		Assert.assertEquals(slotNums.size(), 2);
		Iterator<Integer> slotNumIt = slotNums.iterator();
		Assert.assertEquals(slotNumIt.next().intValue(), 2);
		Assert.assertEquals(slotNumIt.next().intValue(), 4);

		slotNums = parkingLotService.getSlotNumbersForColour(0, "Black");
		Assert.assertEquals(slotNums.size(), 1);
		slotNumIt = slotNums.iterator();
		Assert.assertEquals(slotNumIt.next().intValue(), 3);

		slotNums = parkingLotService.getSlotNumbersForColour(0, "Blue");
		Assert.assertEquals(slotNums.size(), 0);
	}

	@Test
	public void testPrintSlotForRegNum() {
		MultiLevelParkingLot parkingLotService = new MultiLevelParkingLot(1, 5);
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-1234", "White");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-9999", "Red");
		parkingLotService.park(VehicleType.CAR, "KA-01-BB-0001", "Black");
		parkingLotService.park(VehicleType.CAR, "KA-01-HH-7777", "Red");
		int slotNum = parkingLotService.getSlotForRegNum(0, "KA-01-HH-1234");
		Assert.assertEquals(slotNum, 1);
		slotNum = parkingLotService.getSlotForRegNum(0, "KA-01-BB-0001");
		Assert.assertEquals(slotNum, 3);
		slotNum = parkingLotService.getSlotForRegNum(0, "Blue");
		Assert.assertEquals(slotNum, -1);
	}

}
