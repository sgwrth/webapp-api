package de.asiegwarth.jwtaws.upload;

public class UploadFileNotFoundException extends UploadException {

    public UploadFileNotFoundException(String message) {
		super(message);
	}

	public UploadFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
