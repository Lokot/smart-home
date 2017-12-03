package ru.skysoftlab.smarthome.navigation;

import ru.skysoftlab.skylibs.web.navigation.NavigationService;

public interface NavigationMainMenu extends NavigationService {

	public interface ConfigMenuOrder {
		public static final int CONFIG_CONFIG = 0;
		public static final int CONFIG_USERS = 1;
	}

	public static interface HeatingMenuNames {
		public static final String ALARMS = "alarm";
		public static final String BOILERS = "boilers";
		public static final String HEATING = "heating";
		public static final String HEATING_MAIN = "heating";
		public static final String SENSORS = "sensors";
		public static final String STATISTIC = "statistic";
		public static final String VALVES = "valves";
	}

	public static interface HeatingMenuOrder {
		public static final int ALARMS = 5;
		public static final int BOILERS = 3;
		public static final int MAIN = 0;
		public static final int SENSORS = 2;
		public static final int STATISTIC = 6;
		public static final int SYSTEM = 4;
		public static final int VALVES = 1;
	}

	public static interface MainMenuNames {
		// public static final String CONFIG = "config";
		// public static final String LOGIN = "login";
		public static final String SYSTEM = "system";
		public static final String USERS = "users";
	}

	public static interface MainMenuOrder {
		public static final int CONFIG_ = 0;
		public static final int HEATING_ = 1;
	}

}
