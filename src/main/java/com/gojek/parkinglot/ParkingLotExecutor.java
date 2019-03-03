package com.gojek.parkinglot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import com.gojek.parkinglot.exceptions.InvalidCommandException;
import com.gojek.parkinglot.services.ParkingLotService;
import com.gojek.parkinglot.vehicles.VehicleType;

public class ParkingLotExecutor {

	ParkingLotService parkingLotService;

	public static void main(String[] args) throws InvalidCommandException, FileNotFoundException {
		ParkingLotExecutor executor = new ParkingLotExecutor();
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
		 parkingLotService = new ParkingLotService(1, noOfSlots);
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
			parkingLotService.park(VehicleType.CAR, params[1], params[2]);
			break;
		case "status":
			parkingLotService.printStatus(0);
			break;
		case "leave":
			int slotNumber = Integer.parseInt(params[1]);
			parkingLotService.unpark(0, slotNumber);
			break;
		case "registration_numbers_for_cars_with_colour":
			parkingLotService.printRegNumbersForColour(0, params[1]);
			break;
		case "slot_numbers_for_cars_with_colour":
			parkingLotService.printSlotNumbersForColour(0, params[1]);
			break;
		case "slot_number_for_registration_number":
			parkingLotService.printSlotForRegNum(0, params[1]);
			break;
		default:
			System.out.println("Invalid command. Please try again.");
			break;
		}
	}
}
