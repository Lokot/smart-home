package ru.skysoftlab.smarthome.heating.cdi;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;

import org.owfs.jowfsclient.OwfsConnectionConfig;

import com.vaadin.cdi.UIScoped;

//@SessionScoped
@UIScoped
public class OwfsProducer implements Serializable {

	private static final long serialVersionUID = 7565939967506122425L;

	@Resource(name = "owfsServerUrl")
	private String url;

	@Produces
	public OwfsConnectionConfig getOwfsConnectionConfig() {
		OwfsConnectionConfig rv = null;
		// String url;
		// try {
		// url = (String) (new InitialContext())
		// .lookup("java:comp/env/owfsServerUrl");
		String[] urlParams = url.split(":");
		rv = new OwfsConnectionConfig(urlParams[0],
				Integer.valueOf(urlParams[1]));
		// } catch (NamingException e) {
		// e.printStackTrace();
		// rv = new OwfsConnectionConfig("localhost", 3000);
		// }
		return rv;
	}
}
