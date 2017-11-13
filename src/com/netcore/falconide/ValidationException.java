package com.netcore.falconide;

/**
 * @author bhaswanthg
 *
 */
public class ValidationException extends Exception {
	public ValidationException() {
		super("Please check if all madatory fields in email are set.");
	}

	public ValidationException(String msg) {
		super(msg);
	}
}
