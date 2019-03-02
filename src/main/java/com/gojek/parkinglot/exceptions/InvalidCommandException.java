package com.gojek.parkinglot.exceptions;

@SuppressWarnings("serial")
public class InvalidCommandException extends Exception {
	public InvalidCommandException(String message) {
		super(message);
	}
}
