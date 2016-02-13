package org.psa.vaadinauth.secure;

import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.FailedLoginException;

import org.apache.openejb.core.security.jaas.LoginProvider;

public class SimpleLoginProvider implements LoginProvider {

	@Override
	public List<String> authenticate(String user, String password) throws FailedLoginException {
		if ("admin".equals(user) && "admin".equals(password)) {
            return Arrays.asList("ADMIN");
        }
		if ("user".equals(user) && "user".equals(password)) {
            return Arrays.asList("USER");
        }
        throw new FailedLoginException();
	}

}
