package de.online.kuehlschrank.onlineKuehlschrank.validation;

import com.vaadin.data.validator.RegexpValidator;

public class EanValidator extends RegexpValidator {
	
	private static final long serialVersionUID = 8816364859523042171L;

	public EanValidator(String errorMessage) {
        super(
                "([0-9]{13})|([0-9]{8})",
                true, errorMessage);
    }
}
