package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesScanner;

import ru.skysoftlab.smarthome.heating.owfs.Ds18bAlarmingDeviceListener;
import ru.skysoftlab.smarthome.heating.owfs.IAlarmScanner;
import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler;

/**
 * Сканер датчиков.
 * 
 * @author Артём
 *
 */
@Singleton
@Startup
// @ApplicationScoped
// @Singleton
public class AlarmScannerBean implements IAlarmScanner {

	private static final long serialVersionUID = 4027430659678255880L;

	@Inject
	private AlarmingDevicesScanner scanner;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Inject
	private TempAlarmHandler tempAlarmHandler;

	@PostConstruct
	public void init() {
		System.out
				.println("*****************************************************");
		System.out.println("*INIT*");
		System.out.println("*" + scanner + "*");
		System.out.println("*" + sensorsProvider + "*");
		System.out.println("*" + tempAlarmHandler + "*");
		System.out
				.println("*****************************************************");
		try {
			for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
				setAlarmingDeviceHandler(config);
			}
		} catch (IOException | OwfsException e) {
			e.printStackTrace();
		}
	}

	// public void init(@Observes @Initialized(ApplicationScoped.class) Object
	// init) {
	// System.out.println("*init(@Observes @Initialized(ApplicationScoped.class)*");
	// }
	//
	// public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object
	// init) {
	// System.out.println("*destroy(@Observes @Destroyed(ApplicationScoped.class)*");
	// }

	@Override
	public void setAlarmingDeviceHandler(IDs18bConfig config)
			throws IOException, OwfsException {
		if (scanner.isAlarmingDeviceOnList(config.getDeviceName())) {
			scanner.removeAlarmingDeviceHandler(config.getDeviceName());
		}
		scanner.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(
				config, tempAlarmHandler));
	}

	@Override
	public void setInterval(int interval) {
		scanner.setPeriodInterval(interval);
	}

}
