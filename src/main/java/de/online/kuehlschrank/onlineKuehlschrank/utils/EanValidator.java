package de.online.kuehlschrank.onlineKuehlschrank.utils;

import com.vaadin.data.validator.RegexpValidator;

public class EanValidator extends RegexpValidator {
	
    public EanValidator(String errorMessage) {
        super(
                "[0-9]{12}",
                true, errorMessage);
    }
}
