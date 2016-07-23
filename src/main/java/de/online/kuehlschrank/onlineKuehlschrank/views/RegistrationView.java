package de.online.kuehlschrank.onlineKuehlschrank.views;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.lapi.Lapi;
import de.online.kuehlschrank.onlineKuehlschrank.container.User;
import de.online.kuehlschrank.onlineKuehlschrank.controle.UserControle;
import de.online.kuehlschrank.onlineKuehlschrank.exceptions.RegistrationException;
import de.online.kuehlschrank.onlineKuehlschrank.utils.LapiKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.utils.ViewKeynames;
import de.online.kuehlschrank.onlineKuehlschrank.validation.PasswordValidator;
import de.online.kuehlschrank.onlineKuehlschrank.validation.UsernameValidator;

public class RegistrationView extends VerticalLayout implements View {

	private Lapi lapi;

	private UserControle userControle;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3645447358966856980L;

	@Override
	public void enter(ViewChangeEvent event) {
		lapi = Lapi.getInstance();
		userControle = UserControle.getInstance();
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		TextField email = new TextField();
		email.addValidator(new EmailValidator(lapi
				.getText(LapiKeynames.EMAIL_VALIDATOR)));
		email.setCaption(lapi.getText(LapiKeynames.EMAIL));
		email.setRequired(false);

		TextField emailConfirm = new TextField();
		emailConfirm.addValidator(new EmailValidator(lapi
				.getText(LapiKeynames.EMAIL_VALIDATOR)));
		emailConfirm.setCaption(lapi.getText(LapiKeynames.EMAIL_CONFIRM));
		emailConfirm.setRequired(false);

		TextField username = new TextField();
		username.addValidator(new UsernameValidator(lapi
				.getText(LapiKeynames.USERNAME_VALIDATOR)));
		username.setCaption(lapi.getText(LapiKeynames.USERNAME));
		username.setRequired(false);

		PasswordField password = new PasswordField();
		password.addValidator(new PasswordValidator(lapi
				.getText(LapiKeynames.PASSWORD_VALIDATOR)));
		password.setCaption(lapi.getText(LapiKeynames.PASSWORD));
		password.setRequired(false);
		password.setRequiredError(lapi
				.getText(LapiKeynames.PASSWORD_REQUIRED_ERROR));

		PasswordField passwordConfirm = new PasswordField();
		passwordConfirm.setCaption(lapi.getText(LapiKeynames.PASSWORD_CONFIRM));
		passwordConfirm.setRequired(false);
		passwordConfirm.setRequiredError(lapi
				.getText(LapiKeynames.PASSWORD_REQUIRED_ERROR));

		Button signUpButton = new Button(
				lapi.getText(LapiKeynames.SIGN_UP_BUTTON),
				FontAwesome.USER_PLUS);
		signUpButton
				.addClickListener(e -> {
					if (validateTextFields(username, password, passwordConfirm,
							email, emailConfirm)) {
						User user = new User(email.getValue(), passwordConfirm
								.getValue(), username.getValue());

						if (userControle.checkUsername(user)) {
							username.setRequired(true);
							username.setRequiredError(lapi
									.getText(LapiKeynames.USERNAME_IN_USE));
							username.setValue("");
						} else if (userControle.checkEmail(user)) {
							email.setRequired(true);
							email.setRequiredError(lapi
									.getText(LapiKeynames.EMAIL_IN_USE));
							email.setValue("");
						} else {
							try {
								userControle.registration(user);
								ViewKeynames.gotoView(ViewKeynames.LOGIN);
							} catch (RegistrationException e1) {
								password.setValue("");
								passwordConfirm.setValue("");
								Notification.show(
										lapi.getText(LapiKeynames.ERROR_TITEL),
										lapi.getText(LapiKeynames.SIGNUP_ERROR_MESSAGE),
										Notification.Type.ERROR_MESSAGE);
							}
						}
					} else {
						password.setValue("");
						passwordConfirm.setValue("");
						Notification.show(
								lapi.getText(LapiKeynames.ERROR_TITEL),
								lapi.getText(LapiKeynames.SIGNUP_ERROR_MESSAGE),
								Notification.Type.ERROR_MESSAGE);
					}

				});

		addComponents(username, email, emailConfirm, password, passwordConfirm,
				signUpButton);
		setMargin(true);
		setSpacing(true);
	}

	private boolean validateTextFields(TextField username,
			PasswordField password, PasswordField passwordConfirm,
			TextField email, TextField emailConfirm) {
		if (!userControle.validateSignUpEmail(email.getValue(),
				emailConfirm.getValue())) {
			emailConfirm.setRequired(true);
			email.setRequired(true);
			email.setRequiredError(lapi
					.getText(LapiKeynames.EMAIL_REQUIRED_ERROR));
			emailConfirm.setRequiredError(lapi
					.getText(LapiKeynames.EMAIL_REQUIRED_ERROR));
			return false;
		}
		if (!userControle.validateSignUpUsername(username.getValue())) {
			username.setRequired(true);
			return false;
		}
		if (!userControle.validateSignUpPassword(password.getValue(),
				passwordConfirm.getValue())) {
			passwordConfirm.setRequired(true);
			password.setRequired(true);
			return false;
		}
		return true;
	}

}
