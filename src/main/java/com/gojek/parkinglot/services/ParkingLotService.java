package com.gojek.parkinglot.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.exceptions.InvalidCommandException;
import com.gojek.parkinglot.slots.Slot;
import com.gojek.parkinglot.vehicles.Vehicle;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotService {

	private MultiLevelParkingLot multiLevelparkingLot;

	public static void main(String[] args) throws InvalidCommandException, FileNotFoundException {
		ParkingLotService executor = new ParkingLotService();
		executor.execute(args);
	}

	private void execute(String[] args) throws FileNotFoundException, InvalidCommandException {
		if (args.length > 0) {
			process(new FileInputStream(args[0]));
		} else {
			process(System.in);
		}
	}

	private void process(InputStream inputStream) throws InvalidCommandException {
		Scanner scanner = new Scanner(inputStream);
		String currentLine = scanner.nextLine();
		String params[] = currentLine.split(" ");
		if (!params[0].equals("create_parking_lot")) {
			scanner.close();
			throw new InvalidCommandException("First command should be 'create_parking_lot'");
		}
		int noOfSlots = Integer.parseInt(params[1]);
		multiLevelparkingLot = new MultiLevelParkingLot(1, noOfSlots);
		System.out.println("Created a parking lot with " + noOfSlots + " slots");
		while (scanner.hasNextLine() && !(currentLine = scanner.nextLine().trim()).equalsIgnoreCase("exit")) {
			params = currentLine.trim().split(" ");
			processCommand(params);
		}
		scanner.close();
	}

	private void processCommand(String[] params) {
		switch (params[0]) {
		case "park":
			int slot = multiLevelparkingLot.park(VehicleType.CAR, params[1], params[2]);
			if (slot > 0) {
				System.out.println("Allocated slot number: " + slot);
			} else {
				System.out.println("Sorry, parking lot is full");
			}
			break;
		case "status":
			System.out.format("%-12s%-19s%-6s", "Slot No.", "Registration No", "Colour");
			Collection<Slot> slots = multiLevelparkingLot.getStatusForLevel(0);
			slots.forEach((s) -> {
				Vehicle vehicle = s.getParkedVehicle();
				System.out.format("\n%-12s%-19s%s", vehicle.getSlot().getLotNumber(), vehicle.getRegNumber(),
						vehicle.getColour());
			});
			System.out.println();
			break;
		case "leave":
			int slotNumber = Integer.parseInt(params[1]);
			boolean success = multiLevelparkingLot.unparkAtLevel(0, slotNumber);
			if (success)
				System.out.println("Slot number " + slotNumber + " is free");
			break;
		case "registration_numbers_for_cars_with_colour":
			Collection<String>  regNumbers = multiLevelparkingLot.getRegNumbersForColour(0, params[1]);
			System.out.println(String.join(", ", regNumbers));
			break;
		case "slot_numbers_for_cars_with_colour":
			Collection<Integer> slotNumbers = multiLevelparkingLot.getSlotNumbersForColour(0, params[1]);
			System.out.println(
					String.join(", ", slotNumbers.stream().map(sn -> "" + sn.intValue()).collect(Collectors.toList())));
			break;
		case "slot_number_for_registration_number":
			int slNum =multiLevelparkingLot.getSlotForRegNum(0, params[1]);
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
