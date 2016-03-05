package ru.skysoftlab.smarthome.heating.security;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.vaadin.cdi.access.JaasAccessControl;

@Alternative
public class Authenticator extends JaasAccessControl implements Serializable {

	private static final long serialVersionUID = -8775780572051229559L;

	public Authenticator() {
		String path = this.getClass().getResource("/login.config").getPath();
		System.setProperty("java.security.auth.login.config", path);
	}

	public void login(String user, String password, HttpServletRequest request)
			throws ServletException {
		request.login(user, password);
	}

	public void logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
}
