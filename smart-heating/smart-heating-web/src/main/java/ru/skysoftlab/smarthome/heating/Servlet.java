package ru.skysoftlab.smarthome.heating;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

/**
 * Сервлет для подгрузки widgetset.
 * 
 * @author Артём
 *
 */
@WebServlet(value = "/*", asyncSupported = true, initParams = {
		@WebInitParam(name = "session-timeout", value = "60"),
		@WebInitParam(name = "UIProvider", value = "com.vaadin.cdi.CDIUIProvider") })
@VaadinServletConfiguration(productionMode = false, ui = MainVaadinUI.class, closeIdleSessions = true, widgetset = "ru.skysoftlab.smarthome.heating.widgetset.VaadinauthWidgetset")
public class Servlet extends VaadinServlet {

	private static final long serialVersionUID = 2918754860952978590L;

}