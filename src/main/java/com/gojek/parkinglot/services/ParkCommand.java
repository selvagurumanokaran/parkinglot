package com.gojek.parkinglot.services;

import java.util.List;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkCommand implements Command {

	private String regNo;
	private String color;
	private ParkingLot multiLevelparkingLot;

	public ParkCommand(ParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
		this.regNo = arguments.get(0);
		this.color = arguments.get(1);
	}

	public void execute() {
		int slot = multiLevelparkingLot.park(VehicleType.CAR, regNo, color);
		if (slot > 0) {
			System.out.println("Allocated slot number: " + slot);
		} else {
			System.out.println("Sorry, parking lot is full");
		}

	}

}
