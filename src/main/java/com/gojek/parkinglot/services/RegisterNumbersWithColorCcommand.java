package com.gojek.parkinglot.services;

import java.util.Collection;
import java.util.List;

import com.gojek.parkinglot.MultiLevelParkingLot;

public class RegisterNumbersWithColorCcommand implements Command {

	private MultiLevelParkingLot multiLevelparkingLot;
	private String color;

	public RegisterNumbersWithColorCcommand(MultiLevelParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
		this.color = arguments.get(0);
	}

	@Override
	public void execute() {
		Collection<String> regNumbers = multiLevelparkingLot.getRegNumbersForColour(0, this.color);
		System.out.println(String.join(", ", regNumbers));
	}

}
