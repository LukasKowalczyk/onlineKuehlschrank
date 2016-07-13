package de.online.kuehlschrank.onlineKuehlschrank.views;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.KnownView;
import de.online.kuehlschrank.onlineKuehlschrank.utils.UsernameValidator;

public class LoginView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
		final HorizontalLayout buttonlayout = new HorizontalLayout();

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		final TextField email = new TextField();
		email.addValidator(new EmailValidator("Deine Email ist nicht richtig!"));
		email.setCaption("Email:");
		email.setRequired(false);
		email.setRequiredError("Bitte gib deine Email ein!");

		final PasswordField password = new PasswordField();
		password.setCaption("Passwort:");
		password.setRequired(false);
		password.setRequiredError("Bitte gib dein Passwort ein!");

		Button loginButton = new Button("Login", FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {

			User user = new User(email.getValue(), password.getValue(), null);

			UserControle userControle = UserControle.getInstance();
			try {
				userControle.checkLogin(user);
				UI.getCurrent().getNavigator()
						.navigateTo(KnownView.MAIN.getName());
				Notification.show("Danke " + user.getName()
						+ "! Dein Passwort ist >" + user.getPassword() + "<",
						Notification.Type.TRAY_NOTIFICATION);

			} catch (LoginException e1) {
				Notification.show("Fehler", "Email oder Passwort ist falsch",
						Notification.Type.ERROR_MESSAGE);
				password.setRequired(true);
				email.setRequired(true);
				email.setValue("");
				password.setValue("");
			}

		});
		Button signUpButton = new Button("Registrieren", FontAwesome.USER_PLUS);
		signUpButton.addClickListener(e -> {
			Notification.show("Vielen Dank für dein Interesse!",
					Notification.Type.TRAY_NOTIFICATION);
			UI.getCurrent().getNavigator()
					.navigateTo(KnownView.REGISTRATION.getName());
		});

		buttonlayout.addComponents(loginButton, signUpButton);
		buttonlayout.setSpacing(true);

		addComponents(email, password, buttonlayout);
		setMargin(true);
		setSpacing(true);

	}

}
