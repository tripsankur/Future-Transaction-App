package com.trips.ankur.ftw.exceptions;

/**
 * 
 * @author tripaank
 *
 */

public class InvalidFieldFormatException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 889615702395878814L;

	public InvalidFieldFormatException(String message) {
        super(message);
    }
}
