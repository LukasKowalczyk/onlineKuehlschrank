package de.online.kuehlschrank.onlineKuehlschrank.exceptions;

public class DatenbankException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatenbankException() {
		super();
	}

	public DatenbankException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public DatenbankException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DatenbankException(String arg0) {
		super(arg0);
	}

	public DatenbankException(Throwable arg0) {
		super(arg0);
	}

}
