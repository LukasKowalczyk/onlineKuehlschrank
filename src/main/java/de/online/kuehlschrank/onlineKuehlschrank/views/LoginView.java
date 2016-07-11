package de.online.kuehlschrank.onlineKuehlschrank.views;

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

public class LoginView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
		final HorizontalLayout buttonlayout = new HorizontalLayout();

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		final TextField username = new TextField();
		username.setCaption("Username:");
		username.setRequired(false);
		username.setRequiredError("Bitte gib deinen Usernamen ein!");
		
		final PasswordField password = new PasswordField();
		password.setCaption("Passwort:");
		password.setRequired(false);
		password.setRequiredError("Bitte gib dein Passwort ein!");

		Button loginButton = new Button("Login", FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {

			User user = new User(username.getValue(), password.getValue());

			UserControle userControle = UserControle.getInstance();
			try {
				userControle.checkLogin(user);
				UI.getCurrent().getNavigator()
						.navigateTo(KnownView.MAIN.getName());
				Notification.show("Danke " + user.getName()
						+ "! Dein Passwort ist >" + user.getPassword() + "<",
						Notification.Type.TRAY_NOTIFICATION);

			} catch (LoginException e1) {
				Notification.show("Fehler",
						"Username oder Passwort ist falsch",
						Notification.Type.ERROR_MESSAGE);
				password.setRequired(true);
				username.setRequired(true);
				username.setValue("");
				password.setValue("");
			}

		});
		Button registrationButton = new Button("Registrieren", FontAwesome.REGISTERED);
		registrationButton.addClickListener(e -> {
			Notification.show("Vielen Dank für dein Interesse!",
					Notification.Type.TRAY_NOTIFICATION);
			UI.getCurrent().getNavigator()
			.navigateTo(KnownView.REGISTRATION.getName());
		});

		buttonlayout.addComponents(loginButton, registrationButton);
		buttonlayout.setSpacing(true);

		addComponents(username, password, buttonlayout);
		setMargin(true);
		setSpacing(true);

	}

}
