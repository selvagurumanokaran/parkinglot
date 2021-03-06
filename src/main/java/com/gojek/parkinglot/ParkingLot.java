package com.gojek.parkinglot;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.gojek.parkinglot.slots.CarSlot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Car;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLot {
	private Queue<Slot> availableSlots;
	private Map<Integer, Slot> parkedSlots;
	private int level;

	public ParkingLot(int noOfSlots) {
		availableSlots = new PriorityQueue<>(noOfSlots, Comparator.comparing(Slot::getLotNumber));
		IntStream.range(0, noOfSlots).forEach(cnt -> availableSlots.add(new CarSlot(cnt + 1)));
		parkedSlots = new TreeMap<>();
	}

	public ParkingLot(int level, int noOfSlots) {
		this(noOfSlots);
		this.level = level;
	}

	public int park(VehicleType type, String regNumber, String colour) {

		Optional<Slot> slotOptional = parkedSlots.values().stream()
				.filter(s -> s.getParkedVehicle().getRegNumber().equalsIgnoreCase(regNumber)).findFirst();
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

	public Collection<String> getRegNumbersForColour(String color) {
		return getSlotStreamForColour(color).map(slot -> slot.getParkedVehicle().getRegNumber())
				.collect(Collectors.toList());
	}

	private Stream<Slot> getSlotStreamForColour(String color) {
		return parkedSlots.values().stream()
				.filter(slot -> slot.getParkedVehicle().getColour().equalsIgnoreCase(color));
	}

	public Collection<Integer> getSlotsForColour(String color) {
		return getSlotStreamForColour(color).map(slot -> (Integer) slot.getLotNumber()).collect(Collectors.toList());
	}

	public int getSlotForRegNum(String regNum) {
		Optional<Slot> optional = parkedSlots.values().stream()
				.filter(slot -> slot.getParkedVehicle().getRegNumber().equalsIgnoreCase(regNum)).findFirst();
		if (optional.isPresent()) {
			return optional.get().getLotNumber();
		}
		return -1;
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

	public int getLevel() {
		return level;
	}

	public boolean isSlotAvailble() {
		return availableSlots.size() > 0;
	}
}
