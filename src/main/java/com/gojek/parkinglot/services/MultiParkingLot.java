package com.gojek.parkinglot.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.VehicleType;

public class MultiParkingLot implements ParkingLot {
	private List<MultiLevelParkingLot> parkingLots;

	public MultiParkingLot() {
		parkingLots = new ArrayList<>();
	}

	public void createParkingLot(int noOfSlots) {
		parkingLots.add(new MultiLevelParkingLot(1, noOfSlots));
	}

	@Override
	public int park(VehicleType type, String regNumber, String colour) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean unpark(int slotNumber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Slot> getAllParkedSlots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getRegNumbersForColour(String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Integer> getSlotsForColour(String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSlotForRegNum(String regNum) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
