package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ru.skysoftlab.smarthome.heating.NavigationEvent;
import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.security.Authenticator;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@CDIView(NavigationService.LOGIN)
public class LoginView extends CustomComponent implements View,
		Button.ClickListener {
	private static final long serialVersionUID = 7457479619765665383L;

	@Inject
	private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

	@Inject
	private Authenticator authenticator;

	private TextField user = new TextField("User:");
	private PasswordField password = new PasswordField("Password:");
	private Button loginButton = new Button("Login", this);

	@Override
	public void enter(ViewChangeEvent event) {
		setSizeFull();

		user.setWidth("300px");
		user.setRequired(true);
		user.setInvalidAllowed(false);

		password.setWidth("300px");
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		VerticalLayout fields = new VerticalLayout(user, password, loginButton,
				new Label("Username/password for admin: admin/admin"),
				new Label("and for simple user: user/user"));
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
		setCompositionRoot(viewLayout);
		user.focus();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		String username = user.getValue();
		String password = this.password.getValue();
		try {
//			JaasAccessControl.login(username, password);
			authenticator.login(username, password,
					(HttpServletRequest) VaadinService.getCurrentRequest());
			getSession().setAttribute("user", username);
			navigationEvent.fire(new NavigationEvent(NavigationService.MAIN));
		} catch (ServletException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}
}