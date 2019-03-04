package com.gojek.parkinglot;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.VehicleType;

public class MultiLevelParkingLot {
	private Queue<ParkingLot> parkingLots;

	public MultiLevelParkingLot(int noOfLevels, int noOfSlots) {
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

	public int park(VehicleType type, String regNumber, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot();
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			int slot = parkingLot.park(type, regNumber, colour);
			return slot;
		}
		return -1;
	}

	public Collection<Slot> getStatusForLevel(int level) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			return parkingLot.getAllParkedSlots();
		}
		return Collections.emptyList();
	}

	public boolean unparkAtLevel(int level, int slotNumber) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			return parkingLot.unpark(slotNumber);
		}
		return false;
	}

	public Collection<String> getRegNumbersForColour(int level, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			return parkingLot.getRegNumbersForColour(colour);
		}
		return Collections.emptyList();
	}

	public Collection<Integer> getSlotNumbersForColour(int level, String colour) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			return parkingLot.getSlotsForColour(colour);
		}
		return Collections.emptyList();
	}

	public int getSlotForRegNum(int level, String regNumber) {
		Optional<ParkingLot> parkingLotOptional = this.getParkingLot(level);
		if (parkingLotOptional.isPresent()) {
			ParkingLot parkingLot = parkingLotOptional.get();
			return parkingLot.getSlotForRegNum(regNumber);
		}
		return -1;
	}
}
