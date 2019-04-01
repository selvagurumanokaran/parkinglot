package com.gojek.parkinglot.services;

import java.util.List;

import com.gojek.parkinglot.MultiLevelParkingLot;

public class SlotNumberWithRegNumCommand implements Command {

	private MultiLevelParkingLot multiLevelparkingLot;
	private String regNo;

	public SlotNumberWithRegNumCommand(MultiLevelParkingLot multiLevelparkingLot, List<String> arguments) {
		this.multiLevelparkingLot = multiLevelparkingLot;
		this.regNo = arguments.get(0);
	}

	@Override
	public void execute() {
		int slNum = multiLevelparkingLot.getSlotForRegNum(0, regNo);
		if (slNum > 0) {
			System.out.println(slNum);
		} else {
			System.out.println("Not found");
		}
	}

}
