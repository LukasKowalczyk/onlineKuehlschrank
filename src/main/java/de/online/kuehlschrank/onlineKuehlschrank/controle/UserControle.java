package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.validator.EmailValidator;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.PasswordValidator;
import de.online.kuehlschrank.onlineKuehlschrank.utils.UsernameValidator;

public class UserControle {

	private static final String COLLECTIONSNAME = "users";

	private DatenbankControle datenbankControle;

	private static UserControle userControle = null;

	private UserControle() {
		datenbankControle = DatenbankControle.getInstance();
	}

	public static UserControle getInstance() {
		if (userControle == null) {
			userControle = new UserControle();
		}
		return userControle;
	}

	public boolean validateUser(User user) {
		if (StringUtils.isBlank(user.getPassword())
				|| !new PasswordValidator("").isValid(user.getPassword())) {
			return false;
		}
		if (StringUtils.isBlank(user.getName())
				|| !new UsernameValidator("").isValid(user.getName())) {
			return false;
		}
		if (StringUtils.isBlank(user.getEmail())
				|| !new EmailValidator("").isValid(user.getEmail())) {
			return false;
		}
		return true;
	}

	public boolean checkLogin(User user) throws LoginException {
		if (!validateUser(user)) {
			throw new LoginException("User nicht Valide");
		}

		// DB-Zugriff
		try {
			return datenbankControle.findInCollection(COLLECTIONSNAME, "email",
					user.getEmail());
		} catch (DatenbankException e) {
			throw new LoginException(e);
		}

	}

	public boolean registration(User user) throws RegistrationException {
		// DB-Zugriff
		if (!validateUser(user)) {
			throw new RegistrationException("Email ist schon vorhanden!");
		}
		try {
			datenbankControle.insertToCollection(COLLECTIONSNAME, user);
		} catch (DatenbankException e) {
			throw new RegistrationException(e);
		}
		return true;
	}

	public boolean checkUsername(User user) {
		// DB-Zugriff
		try {
			return datenbankControle.findInCollection(COLLECTIONSNAME, "name",
					user.getName());
		} catch (DatenbankException e) {
			return false;
		}
	}

	public boolean checkEmail(User user) {
		// DB-Zugriff
		try {
			return datenbankControle.findInCollection(COLLECTIONSNAME, "email",
					user.getEmail());
		} catch (DatenbankException e) {
			return false;
		}
	}
}
