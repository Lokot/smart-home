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
	public static final String SYSTEM = "system";

	
	public static interface MainMenu {
		public static final int CONFIG = 0;
		public static final int ALARM = 1;
	}
	
	public static interface ConfigMenu {
		public static final int GPIO = 0;
		public static final int SENSORS = 1;
		public static final int SYSTEM = 2;
	}
	
	public static interface AlarmMenu {
		public static final int ALARMS = 0;
		public static final int STATISTIC = 1;
	}
	
	

	public void onNavigationEvent(@Observes NavigationEvent event);
}
