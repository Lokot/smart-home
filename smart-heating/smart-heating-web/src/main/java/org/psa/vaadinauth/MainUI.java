package org.psa.vaadinauth;

import org.psa.vaadinauth.secure.Authenticator;
import org.psa.vaadinauth.ui.HeatingMaivView;
import org.psa.vaadinauth.ui.LoginView;
import org.psa.vaadinauth.ui.MainView;

import ru.skysoftlab.smarthome.heating.ui.MenuItems;
import ru.skysoftlab.smarthome.heating.ui.MenuItems.MenuItem;

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
		
		// TODO убрать потом
		getNavigator().addView(LoginView.NAME, LoginView.class);
		getNavigator().addView(MainView.NAME, MainView.class);
		getNavigator().addView(HeatingMaivView.NAME, HeatingMaivView.class);

		registrateNavigationViews();
		
		// слушатель переходов
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

	/**
	 * Регистрируем страницы.
	 */
	public void registrateNavigationViews() {
		for (MenuItems mainItem : MenuItems.values()) {
			if (mainItem.getUrl() != null && mainItem.getViewClass() != null) {
				getNavigator().addView(mainItem.getUrl(),
						mainItem.getViewClass());
			}
			for (MenuItem item : mainItem.getItems()) {
				getNavigator().addView(item.getUrl(), item.getViewClass());
			}
		}
	}
}