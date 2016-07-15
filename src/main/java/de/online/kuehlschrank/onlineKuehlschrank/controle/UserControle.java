package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.validator.EmailValidator;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.PasswordValidator;

public class UserControle {

	private DatabaseControle datenbankControle;

	private static UserControle userControle = null;

	private UserControle() {
		datenbankControle = DatabaseControle.getInstance();
	}

	public static UserControle getInstance() {
		if (userControle == null) {
			userControle = new UserControle();
		}
		return userControle;
	}

	private boolean validateUser(User user) {
		if (StringUtils.isBlank(user.getPassword())
				|| !new PasswordValidator("").isValid(user.getPassword())) {
			return false;
		}
		// if (StringUtils.isBlank(user.getName())
		// || !new UsernameValidator("").isValid(user.getName())) {
		// return false;
		// }
		if (StringUtils.isBlank(user.getEmail())
				|| !new EmailValidator("").isValid(user.getEmail())) {
			return false;
		}
		return true;
	}

	public User checkLogin(User user) throws LoginException {
		if (!validateUser(user)) {
			throw new LoginException("User nicht Valide");
		}

		try {
			if (datenbankControle.findInCollection(User.class, "email",
					user.getEmail())) {
				return datenbankControle.getCollectionElement(User.class,
						"email", user.getEmail());
			}
			return null;
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
			datenbankControle.insertToCollection(user);
		} catch (DatenbankException e) {
			throw new RegistrationException(e);
		}
		return true;
	}

	public boolean checkUsername(User user) {
		// DB-Zugriff
		try {
			return datenbankControle.findInCollection(User.class, "name",
					user.getName());
		} catch (DatenbankException e) {
			return false;
		}
	}

	public boolean checkEmail(User user) {
		// DB-Zugriff
		try {
			return datenbankControle.findInCollection(User.class, "email",
					user.getEmail());
		} catch (DatenbankException e) {
			return false;
		}
	}
}
