package de.online.kuehlschrank.onlineKuehlschrank.utils;

import com.vaadin.data.validator.RegexpValidator;

public class PasswordValidator extends RegexpValidator {
	
    public PasswordValidator(String errorMessage) {
    	//mindestens 7stellen, 1großBuchstabe und 1Ziffer/sonderzeichen
        super(
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$&§=+%!?.-_]).{7,})",
                true, errorMessage);
    }
}