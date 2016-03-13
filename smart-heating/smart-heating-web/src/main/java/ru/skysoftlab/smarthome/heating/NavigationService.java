package ru.skysoftlab.smarthome.heating;

import java.io.Serializable;

import javax.enterprise.event.Observes;

public interface NavigationService extends Serializable {
	
	public static final String MAIN = "";
	public static final String LOGIN = "login";
	public static final String SENSORS = "sensors";
	public static final String GPIO = "gpio";
	public static final String ALARMS = "alarm";
	public static final String STATISTIC = "statistic";
	public static final String CONFIG = "config";
		
    public void onNavigationEvent(@Observes NavigationEvent event);
}
