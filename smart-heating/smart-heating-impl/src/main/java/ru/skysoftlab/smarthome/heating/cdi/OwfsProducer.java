package ru.skysoftlab.smarthome.heating.cdi;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;

import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.alarm.AlarmingDevicesScanner;

public class OwfsProducer implements Serializable {

	private static final long serialVersionUID = 7565939967506122425L;

	@Resource(name = "owfsServerUrl")
	private String url;
	
	@Resource(name = "owfsScannerInterval")
	private Integer interval;

	@Produces
	public OwfsConnectionConfig getOwfsConnectionConfig() {
		OwfsConnectionConfig rv = null;
		String[] urlParams = url.split(":");
		rv = new OwfsConnectionConfig(urlParams[0],
				Integer.valueOf(urlParams[1]));
		return rv;
	}

	@Produces
	public OwfsConnectionFactory getOwfsConnectionFactory() {
		String[] urlParams = url.split(":");
		OwfsConnectionFactory owfsConnectionFactory = new OwfsConnectionFactory(
				urlParams[0], Integer.valueOf(urlParams[1]));
		return owfsConnectionFactory;
	}

	@Produces
	public AlarmingDevicesScanner getAlarmingDevicesScanner() {
		OwfsConnectionFactory owfsConnectionFactory = getOwfsConnectionFactory();
		AlarmingDevicesScanner scanner = owfsConnectionFactory.getAlarmingScanner();
		scanner.setPeriodInterval(interval);
		return scanner;
	}

}
