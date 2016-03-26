package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesScanner;

import ru.skysoftlab.smarthome.heating.owfs.Ds18bAlarmingDeviceListener;
import ru.skysoftlab.smarthome.heating.owfs.IAlarmScanner;
import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;

/**
 * Сканер датчиков.
 * 
 * @author Артём
 *
 */
//@Singleton
//@Startup
public class AlarmScannerBean  { // implements IAlarmScanner

	private static final long serialVersionUID = 4027430659678255880L;
	
//	public static final String INTERVAL = "owfsScannerInterval";
//
//	@Inject
//	// TODO надо переопределять сканер при изменении Url серввера и интервала
//	private AlarmingDevicesScanner scanner;
//
//	@Inject
//	private SensorsAndGpioProvider sensorsProvider;
//
//	@Inject
//	private TempAlarmHandler tempAlarmHandler;

//	@PostConstruct
//	public void init() {
//		try {
//			for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
//				setAlarmingDeviceHandler(config);
//			}
//		} catch (IOException | OwfsException e) {
//			e.printStackTrace();
//		}
//	}

//	@Override
//	public void setAlarmingDeviceHandler(IDs18bConfig config)
//			throws IOException, OwfsException {
//		if (scanner.isAlarmingDeviceOnList(config.getDeviceName())) {
//			scanner.removeAlarmingDeviceHandler(config.getDeviceName());
//		}
//		scanner.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(
//				config, tempAlarmHandler));
//	}
//
//	@Override
//	public void setInterval(int interval) {
//		scanner.setPeriodInterval(interval);
//	}
//	
}
