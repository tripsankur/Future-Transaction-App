package com.trips.ankur.ftw.exceptions;

/**
 * 
 * @author tripaank
 *
 */
public class NotEnoughArgumentException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2356501621282118646L;

	public NotEnoughArgumentException(String message) {
        super(message);
    }
}