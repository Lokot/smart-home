package ru.skysoftlab.smarthome.heating;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.annatations.SimpleQualifier;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;

@NormalUIScoped
public class NavigationServiceImpl implements NavigationService {

	private static final long serialVersionUID = 1L;

	@Inject
	private CDIViewProvider viewProvider;

	@Inject
	private ErrorView errorView;

	@Inject
	@SimpleQualifier(MainVaadinUI.NAME)
	private UI ui;

	@PostConstruct
	public void initialize() {
		if (ui.getNavigator() == null) {
			Navigator navigator = new Navigator(ui, ui);
			navigator.addProvider(viewProvider);
			navigator.setErrorView(errorView);
		}
	}

	@Override
	public void onNavigationEvent(@Observes NavigationEvent event) {
		try {
			boolean isLoggedIn = ui.getSession().getAttribute("user") != null;
			boolean isLoginView = event.getNavigateTo().equals(NavigationService.LOGIN);
			if (!isLoggedIn && !isLoginView) {
				ui.getNavigator().navigateTo(NavigationService.LOGIN);
			} else if (isLoggedIn && isLoginView) {
				// ничего не делаем
			} else {
				ui.getNavigator().navigateTo(event.getNavigateTo());
			}
		} catch (Exception e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
			// throw new RuntimeException(e);
		}
	}

}
