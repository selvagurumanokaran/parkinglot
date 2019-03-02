package com.gojek.parkinglot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

import com.gojek.parkinglot.slots.CarSlot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Car;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLot {
	Queue<Slot> availableSlots;
	List<Slot> parkedSlots;

	public ParkingLot(int noOfSlots) {
		availableSlots = new PriorityQueue<>(noOfSlots, Comparator.comparing(Slot::getLotNumber));
		IntStream.range(0, noOfSlots).forEach((cnt) -> {
			availableSlots.add(new CarSlot(cnt + 1));
		});
		parkedSlots = new ArrayList<>(noOfSlots);
	}

	public int park(VehicleType type, String regNumber, String colour) {
		Vehicle vehicle = ParkingLot.createVehicle(type, regNumber, colour);
		Optional<Slot> optional = Optional.ofNullable(availableSlots.poll());
		if (optional.isPresent()) {
			Slot slot = optional.get();
			slot.setVehicle(vehicle);
			parkedSlots.add(slot);
			return slot.getLotNumber();
		}
		return -1;
	}

	public boolean unpark() {
		return false;
	}

	public List<Slot> getAllParkedSlots() {
		return parkedSlots;
	}

	public List<String> getRegNumbersForColour(String color) {
		return null;
	}

	public int[] getSlotsForColour(String color) {
		return null;
	}

	public int getSlotForRegNum(String regNum) {
		return 0;
	}

	public static Vehicle createVehicle(VehicleType type, String regNumber, String Colour) {
		Vehicle vehicle = null;
		switch (type) {
		case CAR:
			vehicle = new Car(regNumber, Colour);
			break;
		}
		return vehicle;
	}
}
