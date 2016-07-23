package de.online.kuehlschrank.onlineKuehlschrank.controle;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.ui.UI;

import de.online.kuehlschrank.onlineKuehlschrank.container.Food;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.DatenbankException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;
import de.online.kuehlschrank.onlineKuehlschrank.validation.PasswordValidator;

public class UserControle {

	private FoodControle foodControle;

	private static final String NAME = "name";

	private static final String EMAIL = "email";

	private DatabaseControle datenbankControle;

	private static UserControle userControle = null;

	private UserControle() {
		datenbankControle = DatabaseControle.getInstance();
		foodControle = FoodControle.getInstance();
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
			if (datenbankControle.findInCollection(User.class, EMAIL,
					user.getEmail())) {
				return datenbankControle.getCollectionElement(User.class,
						EMAIL, user.getEmail());
			}
			return null;
		} catch (DatenbankException e) {
			throw new LoginException(e);
		}

	}

	public boolean registration(User user) throws RegistrationException {
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
		try {
			return datenbankControle.findInCollection(User.class, NAME,
					user.getName());
		} catch (DatenbankException e) {
			return false;
		}
	}

	public boolean checkEmail(User user) {
		try {
			return datenbankControle.findInCollection(User.class, EMAIL,
					user.getEmail());
		} catch (DatenbankException e) {
			return false;
		}
	}

	public boolean validateSignUpEmail(String email, String emailConfirm) {
		return StringUtils.isNotBlank(email)
				&& StringUtils.isNotBlank(emailConfirm)
				&& StringUtils.equals(emailConfirm, email);
	}

	public boolean validateSignUpPassword(String password,
			String passwordConfirm) {
		return StringUtils.isNotBlank(password)
				&& StringUtils.isNotBlank(passwordConfirm)
				&& StringUtils.equals(passwordConfirm, password);
	}

	public List<Food> deleteFoodInUserSorage(User user, String code) {
		List<Food> userStorage = user.getUserStorage();
		Food selectedFood = null;
		for (Food food : userStorage) {
			if (food.getCode().equals(code)) {
				selectedFood = food;
				break;
			}
		}
		if (selectedFood != null) {
			userStorage.remove(selectedFood);
		}
		return userStorage;
	}

	public void saveUser(User user) throws DatenbankException {
		datenbankControle.updateInCollection(user);
	}

	public void insertFoodInUserstorage(User user, Food food) throws Exception {
		user.addFood(food);
		saveUser(user);
		inserFood(food);
	}

	public static User getCurrentUser() {
		return UI.getCurrent().getSession().getAttribute(User.class);
	}

	public static void setCurrentUser(User user) {
		UI.getCurrent().getSession().setAttribute(User.class, user);
	}

	public void updateFoodInUserstorage(User user, Food food, String code)
			throws Exception {
		user.setUserStorage(deleteFoodInUserSorage(user, code));
		user.addFood(food);
		saveUser(user);
		inserFood(food);
	}

	private void inserFood(Food food) throws Exception {
		if (!foodControle.isKnownFood(food)) {
			foodControle.addNewFood(food);
		}
	}

	public boolean validateSignUpUsername(String username) {
		return StringUtils.isNotBlank(username);
	}
}
