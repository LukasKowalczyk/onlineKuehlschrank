package de.online.kuehlschrank.onlineKuehlschrank.utils;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

import de.online.kuehlschrank.onlineKuehlschrank.OnlineKuehlschrankUI;
import de.online.kuehlschrank.onlineKuehlschrank.views.LoginView;
import de.online.kuehlschrank.onlineKuehlschrank.views.MainView;
import de.online.kuehlschrank.onlineKuehlschrank.views.RegistrationView;

public enum ViewKeynames {
	LOGIN("login"), MAIN("main"), REGISTRATION("registration");
	private String name;

	private ViewKeynames(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static void gotoView(ViewKeynames view) {
		UI.getCurrent().getNavigator().navigateTo(view.getName());
	}

	public static void generarteNavigator(UI ui, OnlineKuehlschrankUI onlineKuehlschrankUI) {
		Navigator navi = new Navigator(ui, onlineKuehlschrankUI);
		navi.addView(ViewKeynames.LOGIN.getName(), LoginView.class);
		navi.addView(ViewKeynames.MAIN.getName(), MainView.class);
		navi.addView(ViewKeynames.REGISTRATION.getName(),
				RegistrationView.class);
	}
}
