package de.online.kuehlschrank.onlineKuehlschrank.exceptions;

public class RegistrationException extends Exception {

	public RegistrationException() {
		super();
	}

	public RegistrationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RegistrationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RegistrationException(String arg0) {
		super(arg0);
	}

	public RegistrationException(Throwable arg0) {
		super(arg0);
	}

}
