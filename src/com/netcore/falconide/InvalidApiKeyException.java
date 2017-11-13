package com.netcore.falconide;

public class InvalidApiKeyException extends ValidationException {
	public InvalidApiKeyException() {
		super("Invalid API Key");
	}
}
