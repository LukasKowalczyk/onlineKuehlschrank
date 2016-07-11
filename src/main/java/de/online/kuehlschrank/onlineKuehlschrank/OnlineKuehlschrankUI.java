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

import de.online.kuehlschrank.onlineKuehlschrank.utils.KnownView;
import de.online.kuehlschrank.onlineKuehlschrank.views.LoginView;
import de.online.kuehlschrank.onlineKuehlschrank.views.MainView;
import de.online.kuehlschrank.onlineKuehlschrank.views.RegistrationView;

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
		navi.addView(KnownView.LOGIN.getName(), LoginView.class);
		navi.addView(KnownView.MAIN.getName(), MainView.class);
		navi.addView(KnownView.REGISTRATION.getName(), RegistrationView.class);
		UI.getCurrent().getNavigator().navigateTo(KnownView.LOGIN.getName());
		
	}

	@WebServlet(urlPatterns = "/*", name = "OnlineKuehlschrankUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = OnlineKuehlschrankUI.class, productionMode = false)
	public static class OnlineKuehlschrankUIServlet extends VaadinServlet {
	}

}
