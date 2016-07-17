package de.online.kuehlschrank.onlineKuehlschrank;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import de.online.kuehlschrank.onlineKuehlschrank.container.User;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828699832482058166L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		User user = UI.getCurrent().getSession().getAttribute(User.class);
		System.out.println(user);
		Navigator navi = new Navigator(this, this);
		navi.addView(KnownView.LOGIN.getName(), LoginView.class);
		navi.addView(KnownView.MAIN.getName(), MainView.class);
		navi.addView(KnownView.REGISTRATION.getName(), RegistrationView.class);
		if (user == null) {
			UI.getCurrent().getNavigator()
					.navigateTo(KnownView.LOGIN.getName());
		} else {
			UI.getCurrent().getNavigator().navigateTo(KnownView.MAIN.getName());
		}

	}

	@WebServlet(urlPatterns = "/*", name = "OnlineKuehlschrankUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = OnlineKuehlschrankUI.class, productionMode = false)
	public static class OnlineKuehlschrankUIServlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5691173458192625910L;
	}

}
