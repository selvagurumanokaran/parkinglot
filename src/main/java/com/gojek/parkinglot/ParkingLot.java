package com.gojek.parkinglot;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.IntStream;

import com.gojek.parkinglot.slots.CarSlot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Car;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLot {
	Queue<Slot> availableSlots;
	Map<Integer, Slot> parkedSlots;

	public ParkingLot(int noOfSlots) {
		availableSlots = new PriorityQueue<>(noOfSlots, Comparator.comparing(Slot::getLotNumber));
		IntStream.range(0, noOfSlots).forEach((cnt) -> {
			availableSlots.add(new CarSlot(cnt + 1));
		});
		parkedSlots = new TreeMap<>();
	}

	public int park(VehicleType type, String regNumber, String colour) {

		Optional<Slot> slotOptional = parkedSlots.values().stream().filter((s) -> {
			return s.getParkedVehicle().getRegNumber() == regNumber;
		}).findFirst();
		if (slotOptional.isPresent()) {
			return slotOptional.get().getLotNumber();
		}
		Optional<Slot> optional = Optional.ofNullable(availableSlots.poll());
		if (optional.isPresent()) {
			Vehicle vehicle = ParkingLot.createVehicle(type, regNumber, colour);
			Slot slot = optional.get();
			slot.setVehicle(vehicle);
			vehicle.setSlot(slot);
			parkedSlots.put(slot.getLotNumber(), slot);
			return slot.getLotNumber();
		}
		return -1;
	}

	public boolean unpark(int slotNumber) {
		Optional<Slot> slotOptional = Optional.ofNullable(parkedSlots.remove(slotNumber));
		if (slotOptional.isPresent()) {
			Slot slot = slotOptional.get();
			slot.setVehicle(null);
			return availableSlots.offer(slot);
		}
		return false;
	}

	public Collection<Slot> getAllParkedSlots() {
		return parkedSlots.values();
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
