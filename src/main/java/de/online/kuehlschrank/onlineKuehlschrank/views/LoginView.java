package de.online.kuehlschrank.onlineKuehlschrank.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.utils.RegistreredView;

public class LoginView extends VerticalLayout implements View {

	@Override
	public void enter(ViewChangeEvent event) {
		final VerticalLayout loglayout = new VerticalLayout();
		final HorizontalLayout buttonlayout = new HorizontalLayout();
		
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		final TextField username = new TextField();
		username.setCaption("Username:");
		final PasswordField passwort = new PasswordField();
		passwort.setCaption("Passwort:");

		Button loginButton = new Button("Login",FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {
			Notification.show(null, "Danke " + username.getValue()
					+ "! Dein Passwort ist >" + passwort.getValue() + "<", Notification.Type.ERROR_MESSAGE);
			UI.getCurrent().getNavigator().navigateTo(RegistreredView.MAIN.getName());
		});
		Button signInButton = new Button("Registrieren",FontAwesome.REGISTERED);
		signInButton.addClickListener(e -> {
			Notification.show(null, "Vielen Dank für dein Interesse!", Notification.Type.WARNING_MESSAGE);
		});

		
		buttonlayout.addComponents(loginButton, signInButton);
		buttonlayout.setSpacing(true);

		Label welcome = new Label("Bitte melde dich an!");
		addComponents(welcome, username, passwort, buttonlayout,loglayout);
		setMargin(true);
		setSpacing(true);

		
		
	}

}
