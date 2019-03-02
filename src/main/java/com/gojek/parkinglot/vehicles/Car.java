package com.gojek.parkinglot.vehicles;

import com.gojek.parkinglot.slots.Slot;

public class Car implements Vehicle {
	private String colour;
	private String regNumber;
	private Slot slot;

	public Car(String regNumber, String colour) {
		this.colour = colour;
		this.regNumber = regNumber;
	}

	public String getColour() {
		return colour;
	}

	public String getRegNumber() {
		return regNumber;
	}

	@Override
	public Slot getSlot() {
		return this.slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

}
