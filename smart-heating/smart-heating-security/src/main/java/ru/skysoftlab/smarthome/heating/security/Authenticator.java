package ru.skysoftlab.smarthome.heating.security;

import java.io.Serializable;

import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import com.vaadin.cdi.access.JaasAccessControl;

@Singleton
@Alternative
public class Authenticator extends JaasAccessControl implements Serializable {

	private static final long serialVersionUID = -8775780572051229559L;

	public Authenticator() {
		System.setProperty("java.security.auth.login.config", this.getClass().getResource("/login.config").getPath());
	}

}
