package com.gojek.parkinglot.operations;

import java.util.Scanner;

import com.gojek.parkinglot.ParkingLot;
import com.gojek.parkinglot.exceptions.InvalidCommandException;
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
					params = currentLine.split(" ");
					switch (params[0]) {
					case "park":
						int slot = parkingLot.park(VehicleType.CAR, params[1], params[2]);
						if (slot > -1) {
							System.out.println("Allocated slot number: " + (slot + 1));
						} else {
							System.out.println("Sorry, parking lot is full");
						}
						break;

					default:
						System.out.println("Invalid command. Please try again.");
						break;
					}
				}
				scanner.close();
			} else {
				scanner.close();
				throw new InvalidCommandException("First command should be 'create_parking_lot'");
			}
		}
	}
}
