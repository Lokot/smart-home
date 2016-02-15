package ru.skysoftlab.smarthome.heating.ui;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ru.skysoftlab.smarthome.heating.MainUI;

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

public class LoginView extends CustomComponent implements View, Button.ClickListener {
	private static final long serialVersionUID = 7457479619765665383L;

	public static final String NAME = "login";

	private final TextField user;

	private final PasswordField password;

	private final Button loginButton;

	public LoginView() {
		setSizeFull();

		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInvalidAllowed(false);

		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		loginButton = new Button("Login", this);

		VerticalLayout fields = new VerticalLayout(user, password, loginButton, new Label("Username/password for admin: admin/admin" ),
				                                                                new Label("and for simple user: user/user" ));
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		user.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		String username = user.getValue();
		String password = this.password.getValue();
		try {
			MainUI mainUI = (MainUI) getUI();
			mainUI.getAuthenticator().login(username, password, (HttpServletRequest) VaadinService.getCurrentRequest());
			getSession().setAttribute("user", username);
			getUI().getNavigator().navigateTo(HeatingMaivView.NAME);
		} catch (ServletException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} 
	}
}