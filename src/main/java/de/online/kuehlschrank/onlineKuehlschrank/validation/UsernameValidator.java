package de.online.kuehlschrank.onlineKuehlschrank.validation;

import com.vaadin.data.validator.RegexpValidator;

public class UsernameValidator extends RegexpValidator {

	private static final long serialVersionUID = 1328277548021690475L;

	public UsernameValidator(String errorMessage) {
		super("^[a-zA-Z0-9_-]{3,20}$", true, errorMessage);
	}
}
