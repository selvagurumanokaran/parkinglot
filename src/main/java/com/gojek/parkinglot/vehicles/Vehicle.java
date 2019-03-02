package com.gojek.parkinglot.vehicles;

import com.gojek.parkinglot.slots.Slot;

public interface Vehicle {

	public String getColour();

	public String getRegNumber();

	public Slot getSlot();

	public void setSlot(Slot slot);
}
