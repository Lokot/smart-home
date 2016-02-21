package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDeviceListener;

import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

public class Ds18bAlarmingDeviceListener implements AlarmingDeviceListener {

	private IDs18bConfig config;
	private final ITempAlarmHandler alarmHandler;

	public Ds18bAlarmingDeviceListener(IDs18bConfig config,
			ITempAlarmHandler alarmHandler) {
		this.config = config;
		this.alarmHandler = alarmHandler;
	}

	@Override
	public String getDeviceName() {
		return config.getDeviceName();
	}

	@Override
	public void onInitialize(OwfsConnection client) throws IOException,
			OwfsException {
		OwsfUtilDS18B.setTemphigh(client, config.getDeviceName(),
				config.getTop());
		OwsfUtilDS18B.setTemplow(client, config.getDeviceName(),
				config.getLow());
	}

	@Override
	public void onAlarm(OwfsConnection client) throws IOException,
			OwfsException {
		Float fastTemp = OwsfUtilDS18B.getFasttemp(client,
				config.getDeviceName());
		if (fastTemp <= config.getLow()) {
			// ON
			alarmHandler.handleAlarm(TempAlarmingEvent.createLowTempEvent(
					config.getDeviceName(), fastTemp));
		} else if (fastTemp >= config.getTop()) {
			// OFF
			alarmHandler.handleAlarm(TempAlarmingEvent.createTopTempEvent(
					config.getDeviceName(), fastTemp));
		}
	}

}
