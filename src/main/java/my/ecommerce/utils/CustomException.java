package my.ecommerce.utils;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	private final String code;
	private final int status;
	private final LogLevel logLevel;
	private final Object data;

	public CustomException(String message, String code, int status, LogLevel logLevel, Object data) {
		super(message);
		this.code = code;
		this.status = status;
		this.logLevel = logLevel;
		this.data = data;
	}

	public enum LogLevel {
		ERROR, WARN, INFO
	}
}
