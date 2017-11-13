package com.netcore.falconide;

public class AttachmentsSizeExceededException extends ValidationException {
	public AttachmentsSizeExceededException() {
		super("Max Attachments size = 40MB");
	}
}
