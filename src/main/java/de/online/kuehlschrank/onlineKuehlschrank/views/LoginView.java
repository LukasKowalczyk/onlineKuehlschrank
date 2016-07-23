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
import com.vaadin.ui.VerticalLayout;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.LoginException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.utils.ViewKeynames;

public class LoginView extends VerticalLayout implements View {

	private Lapi lapi;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3730138824162367154L;

	@Override
	public void enter(ViewChangeEvent event) {
		lapi = Lapi.getInstance();
		final HorizontalLayout buttonlayout = new HorizontalLayout();

		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		final TextField email = new TextField();
		email.addValidator(new EmailValidator(lapi
				.getText(LapiKeynames.EMAIL_VALIDATOR)));
		email.setCaption(lapi.getText(LapiKeynames.EMAIL));
		email.setRequired(false);
		email.setRequiredError(lapi.getText(LapiKeynames.EMAIL_REQUIRED_ERROR));

		final PasswordField password = new PasswordField();
		password.setCaption(lapi.getText(LapiKeynames.PASSWORD));
		password.setRequired(false);
		password.setRequiredError(lapi
				.getText(LapiKeynames.PASSWORD_REQUIRED_ERROR));

		Button loginButton = new Button(
				lapi.getText(LapiKeynames.LOGIN_BUTTON), FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {

			User user = new User(email.getValue(), password.getValue(), null);

			try {
				user = UserControle.getInstance().checkLogin(user);
				UserControle.setCurrentUser(user);
				ViewKeynames.gotoView(ViewKeynames.MAIN);
			} catch (LoginException e1) {
				Notification.show(lapi.getText(LapiKeynames.ERROR_TITEL),
						lapi.getText(LapiKeynames.LOGIN_ERROR_MESSAGE),
						Notification.Type.ERROR_MESSAGE);
				password.setRequired(true);
				email.setRequired(true);
				email.setValue("");
				password.setValue("");
			}

		});
		Button signUpButton = new Button(
				lapi.getText(LapiKeynames.SIGN_UP_BUTTON),
				FontAwesome.USER_PLUS);
		signUpButton.addClickListener(e -> {
			ViewKeynames.gotoView(ViewKeynames.REGISTRATION);
		});

		buttonlayout.addComponents(loginButton, signUpButton);
		buttonlayout.setSpacing(true);

		addComponents(email, password, buttonlayout);
		setMargin(true);
		setSpacing(true);

	}

}
