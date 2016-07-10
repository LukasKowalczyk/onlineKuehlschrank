package de.online.kuehlschrank.onlineKuehlschrank;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ui.UIState.NotificationTypeConfiguration;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.online.kuehlschrank.onlineKuehlschrank.utils.RegistreredView;
import de.online.kuehlschrank.onlineKuehlschrank.views.LoginView;
import de.online.kuehlschrank.onlineKuehlschrank.views.MainView;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("onlineKuehlschrankTheme")
@Widgetset("de.online.kuehlschrank.onlineKuehlschrank.OnlineKuehlschrankWidgetset")
@Title("Online Kühlschrank")
public class OnlineKuehlschrankUI extends UI {


	@Override
	protected void init(VaadinRequest vaadinRequest) {
		
		
		Navigator navi = new Navigator(this, this);
		navi.addView(RegistreredView.LOGIN.getName(), LoginView.class);
		navi.addView(RegistreredView.MAIN.getName(), MainView.class);
		UI.getCurrent().getNavigator().navigateTo(RegistreredView.LOGIN.getName());
		
		
		
		
		
		final VerticalLayout centerLayout = new VerticalLayout();
		final VerticalLayout loglayout = new VerticalLayout();
		final HorizontalLayout buttonlayout = new HorizontalLayout();
		
		centerLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		
		final TextField username = new TextField();
		username.setCaption("Username:");
		final PasswordField passwort = new PasswordField();
		passwort.setCaption("Passwort:");

		Button loginButton = new Button("Login",FontAwesome.SIGN_IN);
		loginButton.addClickListener(e -> {
			Notification.show(null, "Danke " + username.getValue()
					+ "! Dein Passwort ist >" + passwort.getValue() + "<", Notification.Type.ERROR_MESSAGE);
		});
		Button signInButton = new Button("Registrieren",FontAwesome.REGISTERED);
		signInButton.addClickListener(e -> {
			Notification.show(null, "Vielen Dank für dein Interesse!", Notification.Type.WARNING_MESSAGE);
		});

		
		buttonlayout.addComponents(loginButton, signInButton);
		buttonlayout.setSpacing(true);

		Label welcome = new Label("Bitte melde dich an!");
		centerLayout.addComponents(welcome, username, passwort, buttonlayout,loglayout);
		centerLayout.setMargin(true);
		centerLayout.setSpacing(true);

		setContent(centerLayout);
	}

	@WebServlet(urlPatterns = "/*", name = "OnlineKuehlschrankUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = OnlineKuehlschrankUI.class, productionMode = false)
	public static class OnlineKuehlschrankUIServlet extends VaadinServlet {
	}

}
