package com.gojek.parkinglot.slots;

import com.gojek.parkinglot.vehicles.Vehicle;

public class CarSlot implements Slot {

	private Vehicle vehicle;
	private int position;
	
	public CarSlot(int position) {
		this.position = position;
	}

	@Override
	public Vehicle getParkedVehicle() {
		return vehicle;
	}

	@Override
	public int getLotNumber() {
		return position;
	}

	@Override
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

}
