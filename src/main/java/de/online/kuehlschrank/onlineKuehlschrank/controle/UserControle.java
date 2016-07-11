package de.online.kuehlschrank.onlineKuehlschrank.controle;

import org.apache.commons.lang3.StringUtils;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;

public class UserControle {

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
		if (StringUtils.isBlank(user.getPassword())) {
			return false;
		}
		if (StringUtils.isBlank(user.getName())) {
			return false;
		}
		return true;
	}

	public boolean checkLogin(User user) throws LoginException {
		if (!validateUser(user)) {
			throw new LoginException("User nicht Valide");
		}

		// DB-Zugriff
		return true;

	}

	public boolean registration(User user) throws RegistrationException {
		// DB-Zugriff
		if (!validateUser(user)) {
			throw new RegistrationException("Email ist schon vorhanden!");
		}

		return true;
	}

	public boolean checkUsername(User user) {
		// DB-Zugriff
		return false;
	}

}
