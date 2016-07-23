package de.online.kuehlschrank.onlineKuehlschrank.validation;

import com.vaadin.data.validator.RegexpValidator;

public class NumberValidator extends RegexpValidator {
	private static final long serialVersionUID = -8969270035583052190L;

	public NumberValidator(String errorMessage) {
		super("[0-9]*", true, errorMessage);
	}
}
