package com.gojek.parkinglot.services;

import java.util.List;

public class LeaveCommand implements Command {

	private ParkingLot multiLevelparkingLot;
	private int slotNumber;

	public LeaveCommand(ParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
		this.slotNumber = Integer.parseInt(arguments.get(0));
	}

	@Override
	public void execute() {
		boolean success = multiLevelparkingLot.unpark(this.slotNumber);
		if (success)
			System.out.println("Slot number " + slotNumber + " is free");
	}

}
