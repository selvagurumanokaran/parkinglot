package com.gojek.parkinglot.vehicles;

public class Car implements Vehicle {
	private String colour;
	private String regNumber;

	public Car(String regNumber, String colour) {
		this.colour = colour;
		this.regNumber = regNumber;
	}

	public String getColour() {
		return colour;
	}

	public String getRegNumber() {
		return regNumber;
	}

}
