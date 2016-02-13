package org.psa.vaadinauth.secure;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Authenticator {
	
	public Authenticator() {
		System.setProperty("java.security.auth.login.config", this.getClass().getResource("/login.config").getPath());
	}

	public void login(String user, String password, HttpServletRequest request) throws ServletException {
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
