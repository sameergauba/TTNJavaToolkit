/**
 * 
 */
package com.toolkit.exceptions;

/**
 * @author nidhi
 *
 */
public class InvalidAddressException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public InvalidAddressException() {
	}

	/**
	 * @param message
	 */
	public InvalidAddressException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidAddressException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidAddressException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidAddressException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
