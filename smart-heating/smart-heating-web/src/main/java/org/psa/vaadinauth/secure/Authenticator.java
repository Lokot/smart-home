package org.psa.vaadinauth.secure;

import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Authenticator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8775780572051229559L;

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
