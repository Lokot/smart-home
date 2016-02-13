package org.psa.vaadinauth;

import org.psa.vaadinauth.secure.Authenticator;
import org.psa.vaadinauth.ui.HeatingMaivView;
import org.psa.vaadinauth.ui.LoginView;
import org.psa.vaadinauth.ui.MainView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MainUI extends UI {
	private static final long serialVersionUID = 7217734842056348169L;

	private Authenticator authenticator = new Authenticator();

	@Override
	protected void init(VaadinRequest request) {
		new Navigator(this, this);
		getNavigator().addView(LoginView.NAME, LoginView.class);
		getNavigator().addView(MainView.NAME, MainView.class);
		getNavigator().addView(HeatingMaivView.NAME, HeatingMaivView.class);
		getNavigator().addViewChangeListener(new ViewChangeListener() {
			private static final long serialVersionUID = -6019538536768922378L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof LoginView;
				if (!isLoggedIn && !isLoginView) {
					getNavigator().navigateTo(LoginView.NAME);
					return false;
				} else if (isLoggedIn && isLoginView) {
					return false;
				}
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});
	}

	public Authenticator getAuthenticator() {
		return authenticator;
	}
}