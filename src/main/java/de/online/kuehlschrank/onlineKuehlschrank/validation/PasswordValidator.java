package de.online.kuehlschrank.onlineKuehlschrank.validation;

import com.vaadin.data.validator.RegexpValidator;

public class PasswordValidator extends RegexpValidator {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordValidator(String errorMessage) {
    	//mindestens 7stellen, 1großBuchstabe und 1Ziffer/sonderzeichen
        super(
                "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$&§=+%!?.-_]).{7,})",
                true, errorMessage);
    }
}
