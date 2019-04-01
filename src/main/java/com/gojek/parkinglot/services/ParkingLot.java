package com.gojek.parkinglot.services;

import java.util.Collection;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.VehicleType;

public interface ParkingLot {

	public int park(VehicleType type, String regNumber, String colour);

	public boolean unpark(int slotNumber);

	public Collection<Slot> getAllParkedSlots();

	public Collection<String> getRegNumbersForColour(String color);

	public Collection<Integer> getSlotsForColour(String color);

	public int getSlotForRegNum(String regNum);

}
