package de.online.kuehlschrank.onlineKuehlschrank.views;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.KnownView;
import de.online.kuehlschrank.onlineKuehlschrank.utils.PasswordValidator;
import de.online.kuehlschrank.onlineKuehlschrank.utils.UsernameValidator;

public class RegistrationView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		TextField email = new TextField();
		email.addValidator(new EmailValidator("Deine Email ist nicht richtig!"));
		email.setCaption("Email:");
		email.setRequired(false);
	

		TextField username = new TextField();
		username.addValidator(new UsernameValidator("Dein Username enthält ' ' ! ? @ # + ( ) § $ & \" % * ~ ' : ; "));
		username.setCaption("Username:");
		username.setRequired(false);

		PasswordField password = new PasswordField();
		password.addValidator(new PasswordValidator("Das Passwort muss mindestens 7 Zeichen lang sein, Ein Großbuchstaben, Eine Ziffer oder ein Sonderzeichen"));
		password.setCaption("Passwort:");
		password.setRequired(false);
		password.setRequiredError("Bitte gib dein Passwort ein!");

		PasswordField passwordSecond = new PasswordField();
		passwordSecond.setCaption("Passwort Wiederholung:");
		passwordSecond.setRequired(false);
		passwordSecond.setRequiredError("Bitte gib dein Passwort ein!");

		Button signUpButton = new Button("Registrieren", FontAwesome.USER_PLUS);
		signUpButton.addClickListener(e -> {
			Notification.show("Vielen Dank für dein Registrierung!",
					Notification.Type.TRAY_NOTIFICATION);
			if (validateTextFields(username, password, passwordSecond, email)) {
				User user = new User(email.getValue(), passwordSecond
						.getValue(), username.getValue());
				if (UserControle.getInstance().checkUsername(user)) {
					username.setRequired(true);
					username.setRequiredError("Der Username >"
							+ username.getValue() + "< ist schon vorhanden!");
					username.setValue("");
				} else if (UserControle.getInstance().checkEmail(user)) {
					email.setRequired(true);
					email.setRequiredError("Die Email >" + email.getValue()
							+ "< ist schon vorhanden!");
					email.setValue("");
				} else {
					try {
						UserControle.getInstance().registration(user);
						UI.getCurrent().getNavigator()
								.navigateTo(KnownView.LOGIN.getName());
						Notification.show("Danke!",
								"Vielen Dank für deine Registrierung!",
								Notification.Type.HUMANIZED_MESSAGE);

					} catch (RegistrationException e1) {
						password.setValue("");
						passwordSecond.setValue("");
						Notification.show("Fehler",
								"Ihre Eingabe sind nicht komplett",
								Notification.Type.ERROR_MESSAGE);
					}
				}
			} else {
				password.setValue("");
				passwordSecond.setValue("");
				Notification.show("Fehler", "Ihre Eingabe sind nicht komplett",
						Notification.Type.ERROR_MESSAGE);
			}

		});

		addComponents(email, username, password, passwordSecond, signUpButton);
		setMargin(true);
		setSpacing(true);
	}

	private boolean validateTextFields(TextField username,
			PasswordField password, PasswordField passwordSecond,
			TextField email) {
		if (StringUtils.isBlank(email.getValue())) {
			email.setRequired(true);
			email.setRequiredError("Bitte gib deine Email ein!");
			return false;
		}
		if (StringUtils.isBlank(username.getValue())) {
			username.setRequired(true);
			return false;
		}
		if (StringUtils.isBlank(passwordSecond.getValue())
				|| StringUtils.isBlank(password.getValue())
				|| !StringUtils.equals(passwordSecond.getValue(),
						password.getValue())) {
			passwordSecond.setRequired(true);
			password.setRequired(true);
			return false;
		}
		return true;
	}

}
