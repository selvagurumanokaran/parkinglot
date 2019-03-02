package com.gojek.parkinglot.slots;

import com.gojek.parkinglot.vehicles.Vehicle;

public interface Slot {
	public Vehicle getParkedVehicle();

	public int getLotNumber();
	
	public void setVehicle(Vehicle vehicle);
}
