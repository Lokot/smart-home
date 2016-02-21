package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;

import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesScanner;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.owfs.Ds18bAlarmingDeviceListener;
import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler;
import ru.skysoftlab.smarthome.heating.owfs.TempAlarmingEvent;

public class AlarmScannerBean implements ITempAlarmHandler {

	private int interval;
	private String hostName;
	private int portNumber;
	private AlarmingDevicesScanner scanner;

	/**
	 * @param hostName
	 * @param portNumber
	 * @param interval
	 */
	public AlarmScannerBean(String hostName, int portNumber, int interval) {
		this.interval = interval;
		this.hostName = hostName;
		this.portNumber = portNumber;
	}

	public void init() {
		OwfsConnectionFactory owfsConnectionFactory = new OwfsConnectionFactory(
				hostName, portNumber);
		scanner = owfsConnectionFactory.getAlarmingScanner();
		scanner.setPeriodInterval(interval);
		try {
			for (IDs18bConfig config : getDs18bConfigs()) {
				setAlarmingDeviceHandler(config);
			}
		} catch (IOException | OwfsException e) {
			e.printStackTrace();
		}
	}

	public void setAlarmingDeviceHandler(IDs18bConfig config)
			throws IOException, OwfsException {
		if (scanner.isAlarmingDeviceOnList(config.getDeviceName())) {
			scanner.removeAlarmingDeviceHandler(config.getDeviceName());
		}
		scanner.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(
				config, this));
	}

	/**
	 * Заглушка.
	 * 
	 * @return
	 */
	private IDs18bConfig[] getDs18bConfigs() {
		return new IDs18bConfig[] {
				new Sensor("28.8AF530040000", 20F, 25F),
				new Sensor("28.F4E330040000", 20F, 25F),
				new Sensor("28.76E830040000", 20F, 25F),
				new Sensor("28.BC8533040000", 20F, 25F) };
	}

	public static void main(String[] args) {
		AlarmScannerBean scannerBean = new AlarmScannerBean("192.168.0.86",
				3000, 1000 * 15);
		scannerBean.init();
	}

	@Override
	public void handleAlarm(TempAlarmingEvent event) {
		System.out.println(event.getDeviceName() + " = " + event.getType()
				+ " = " + event.getFastTemp());
		
	}

	public void setInterval(int interval) {
		this.interval = interval;
		scanner.setPeriodInterval(interval);
	}

}
