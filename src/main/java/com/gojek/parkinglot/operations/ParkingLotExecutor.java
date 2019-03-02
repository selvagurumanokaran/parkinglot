package com.gojek.parkinglot.operations;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.gojek.parkinglot.ParkingLot;
import com.gojek.parkinglot.exceptions.InvalidCommandException;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotExecutor {

	public static void main(String[] args) throws InvalidCommandException {
		if (args.length > 0) {
			System.out.println("Proccessing file ....");
		} else {
			Scanner scanner = new Scanner(System.in);
			String currentLine = scanner.nextLine();
			String params[] = currentLine.split(" ");
			if (params[0].equals("create_parking_lot")) {
				int noOfSlots = Integer.parseInt(params[1]);
				ParkingLot parkingLot = new ParkingLot(noOfSlots);
				System.out.println("Created a parking lot with " + noOfSlots + " slots");
				while (!(currentLine = scanner.nextLine()).trim().equalsIgnoreCase("exit")) {
					params = currentLine.trim().split(" ");
					processCommand(parkingLot, params);
				}
				scanner.close();
			} else {
				scanner.close();
				throw new InvalidCommandException("First command should be 'create_parking_lot'");
			}
		}
	}

	private static void processCommand(ParkingLot parkingLot, String[] params) {
		switch (params[0]) {
		case "park":
			int slot = parkingLot.park(VehicleType.CAR, params[1], params[2]);
			if (slot > -1) {
				System.out.println("Allocated slot number: " + slot);
			} else {
				System.out.println("Sorry, parking lot is full");
			}
			break;
		case "status":
			Collection<Slot> slots = parkingLot.getAllParkedSlots();
			System.out.println("Slot No. Registration No Colour");
			slots.forEach((s) -> {
				Vehicle vehicle = s.getParkedVehicle();
				System.out.println(
						vehicle.getSlot().getLotNumber() + " " + vehicle.getRegNumber() + " " + vehicle.getColour());
			});
			break;
		case "leave":
			int slotNumber = Integer.parseInt(params[1]);
			parkingLot.unpark(slotNumber);
			System.out.println("Slot number " + slotNumber + " is free");
			break;
		case "registration_numbers_for_cars_with_colour":
			List<String> regNumbers = parkingLot.getRegNumbersForColour(params[1]);
			System.out.println(String.join(", ", regNumbers));
			break;
		case "slot_numbers_for_cars_with_colour":
			List<Integer> slotNumbers = parkingLot.getSlotsForColour(params[1]);
			System.out.println(
					String.join(", ", slotNumbers.stream().map(sn -> "" + sn.intValue()).collect(Collectors.toList())));
			break;
		case "slot_number_for_registration_number":
			int slNum = parkingLot.getSlotForRegNum(params[1]);
			if (slNum > 0) {
				System.out.println(slNum);
			} else {
				System.out.println("Not found");
			}
			break;
		default:
			System.out.println("Invalid command. Please try again.");
			break;
		}
	}
}
