package de.online.kuehlschrank.onlineKuehlschrank.utils;

import com.vaadin.data.validator.RegexpValidator;

public class NumberValidator extends RegexpValidator {
	
    public NumberValidator(String errorMessage) {
        super(
                "[0-9]*",
                true, errorMessage);
    }
}
