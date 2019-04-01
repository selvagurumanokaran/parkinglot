package com.gojek.parkinglot.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.gojek.parkinglot.MultiLevelParkingLot;
import com.gojek.parkinglot.ParkingLot;
import com.gojek.parkinglot.exceptions.InvalidCommandException;

public class ParkingLotService {

	private ParkingLot multiLevelparkingLot;
	private Command command;

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
		multiLevelparkingLot = new ParkingLot(1, noOfSlots);
		System.out.println("Created a parking lot with " + noOfSlots + " slots");
		while (scanner.hasNextLine() && !(currentLine = scanner.nextLine().trim()).equalsIgnoreCase("exit")) {
			params = currentLine.trim().split(" ");
			processCommand(params);
		}
		scanner.close();
	}

	private void processCommand(String[] params) {
		List<String> arguments = new ArrayList<>();
		for (int i = 1; i < params.length; i++) {
			arguments.add(params[i]);
		}

		switch (params[0]) {
		case "park":
			command = new ParkCommand(multiLevelparkingLot, arguments);
			break;
		case "status":
			command = new StatusCommand(multiLevelparkingLot, arguments);
			break;
		case "leave":
			command = new LeaveCommand(multiLevelparkingLot, arguments);
			break;
		case "registration_numbers_for_cars_with_colour":
			command = new RegisterNumbersWithColorCcommand(multiLevelparkingLot, arguments);
			break;
		case "slot_numbers_for_cars_with_colour":
			command = new SlotNumbersWithColorCommand(multiLevelparkingLot, arguments);
			break;
		case "slot_number_for_registration_number":
			command = new SlotNumberWithRegNumCommand(multiLevelparkingLot, arguments);
			break;
		case "create_parking_lot":
			command = new CreateParkingLotCommand(multiLevelparkingLot, arguments);
		}
		command.execute();
	}
}
