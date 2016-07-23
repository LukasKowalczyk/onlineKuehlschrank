package de.online.kuehlschrank.onlineKuehlschrank.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.data.Validator;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;

public class DateValidator implements Validator {

	private static final long serialVersionUID = 238333423364403890L;

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (value instanceof Date) {
			Lapi lapi = Lapi.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(
					lapi.getText(LapiKeynames.DATE_FORMAT));
			String valueDateFormat = sdf.format((Date) value);
			String aktDateFormat = sdf.format(new Date());
			Date valueDate = null;
			Date aktDate = null;
			try {
				valueDate = sdf.parse(valueDateFormat);
				aktDate = sdf.parse(aktDateFormat);
			} catch (ParseException e) {
				throw new InvalidValueException(
						lapi.getText(LapiKeynames.DATE_ERROR));
			}

			if (valueDate.before(aktDate)) {
				throw new InvalidValueException(
						lapi.getText(LapiKeynames.DATE_ERROR));
			}
		}
	}
}
