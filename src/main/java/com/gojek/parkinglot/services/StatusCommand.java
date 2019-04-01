package com.gojek.parkinglot.services;

import java.util.Collection;
import java.util.List;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Vehicle;

public class StatusCommand implements Command {

	private MultiLevelParkingLot multiLevelparkingLot;

	public StatusCommand(MultiLevelParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
	}

	@Override
	public void execute() {
		System.out.format("%-12s%-19s%-6s", "Slot No.", "Registration No", "Colour");
		Collection<Slot> slots = multiLevelparkingLot.getStatusForLevel(0);
		slots.forEach((s) -> {
			Vehicle vehicle = s.getParkedVehicle();
			System.out.format("\n%-12s%-19s%s", vehicle.getSlot().getLotNumber(), vehicle.getRegNumber(),
					vehicle.getColour());
		});
		System.out.println();
	}

}
