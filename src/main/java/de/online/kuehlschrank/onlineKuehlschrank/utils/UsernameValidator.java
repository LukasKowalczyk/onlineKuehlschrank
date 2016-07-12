package de.online.kuehlschrank.onlineKuehlschrank.utils;

import com.vaadin.data.validator.RegexpValidator;

public class UsernameValidator extends RegexpValidator {
	
    public UsernameValidator(String errorMessage) {
        super(
                "^[a-zA-Z0-9_-]{3,20}$",
                true, errorMessage);
    }
}
