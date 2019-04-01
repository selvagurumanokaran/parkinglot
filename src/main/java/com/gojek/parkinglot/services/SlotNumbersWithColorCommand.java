package com.gojek.parkinglot.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.gojek.parkinglot.MultiLevelParkingLot;

public class SlotNumbersWithColorCommand implements Command {

	private MultiLevelParkingLot multiLevelparkingLot;
	private String color;

	public SlotNumbersWithColorCommand(MultiLevelParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
		this.color = arguments.get(0);
	}

	@Override
	public void execute() {
		Collection<Integer> slotNumbers = multiLevelparkingLot.getSlotNumbersForColour(0, color);
		System.out.println(
				String.join(", ", slotNumbers.stream().map(sn -> "" + sn.intValue()).collect(Collectors.toList())));
	}

}
