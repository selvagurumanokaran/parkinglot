package com.gojek.parkinglot.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.gojek.parkinglot.ParkingLot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotService {
	private Queue<ParkingLot> parkingLots;

	public ParkingLotService(int noOfLevels, int noOfSlots) {
		parkingLots = new PriorityQueue<>(noOfLevels, new Comparator<ParkingLot>() {
			@Override
			public int compare(ParkingLot o1, ParkingLot o2) {
				if (o1.getLevel() < o2.getLevel() && o1.isSlotAvailble()) {
					return -1;
				}
				return 1;
			}
		});
		IntStream.range(0, noOfLevels).forEach(cnt -> parkingLots.add(new ParkingLot(noOfSlots)));
	}

	private Optional<ParkingLot> getParkingLot() {
		ParkingLot parkingLot = parkingLots.peek();
		ParkingLot result = null;
		if (parkingLot.isSlotAvailble()) {
			result = parkingLot;
		}
		return Optional.ofNullable(result);
	}

	private Optional<ParkingLot> getParkingLot(int level) {
		return parkingLots.stream().filter(pl -> pl.getLevel() == level).findFirst();
	}

	public void park(VehicleType type, String regNumber, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot();
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			int slot = parkingLot.park(type, regNumber, colour);
			System.out.println("Allocated slot number: " + slot);
		} else {
			System.out.println("Sorry, parking lot is full");
		}
	}

	public void printStatus(int level) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		System.out.format("%-12s%-19s%-6s", "Slot No.", "Registration No", "Colour");
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			Collection<Slot> slots = parkingLot.getAllParkedSlots();
			slots.forEach((s) -> {
				Vehicle vehicle = s.getParkedVehicle();
				System.out.format("\n%-12s%-19s%s", vehicle.getSlot().getLotNumber(), vehicle.getRegNumber(),
						vehicle.getColour());
			});
			System.out.println();
		}
	}

	public void unpark(int level, int slotNumber) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			if (parkingLot.unpark(slotNumber))
				System.out.println("Slot number " + slotNumber + " is free");
		}
	}

	public void printRegNumbersForColour(int level, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		List<String> regNumbers = Collections.emptyList();
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			regNumbers = parkingLot.getRegNumbersForColour(colour);
		}
		System.out.println(String.join(", ", regNumbers));
	}

	public void printSlotNumbersForColour(int level, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			List<Integer> slotNumbers = parkingLot.getSlotsForColour(colour);
			System.out.println(
					String.join(", ", slotNumbers.stream().map(sn -> "" + sn.intValue()).collect(Collectors.toList())));
		}
	}

	public void printSlotForRegNum(int level, String regNumber) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			int slNum = parkingLot.getSlotForRegNum(regNumber);
			if (slNum > 0) {
				System.out.println(slNum);
			} else {
				System.out.println("Not found");
			}
		}

	}
}
