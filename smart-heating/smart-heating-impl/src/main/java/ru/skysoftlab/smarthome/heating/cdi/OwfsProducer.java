package ru.skysoftlab.smarthome.heating.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.alarm.AlarmingDevicesScanner;

import ru.skysoftlab.smarthome.heating.annatations.AppProperty;

/**
 * Провайдер для работы с OneWare сетью.
 * 
 * @author Артём
 *
 */
public class OwfsProducer implements Serializable {

	private static final long serialVersionUID = 7565939967506122425L;

	@Inject
	@AppProperty("owfsServerUrl")
	private String url;

	@Inject
	@AppProperty("owfsScannerInterval")
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
		AlarmingDevicesScanner scanner = owfsConnectionFactory
				.getAlarmingScanner();
		scanner.setPeriodInterval(interval);
		return scanner;
	}

	public void closeConfig(@Disposes OwfsConnectionConfig config) {
		config = null;
	}

	public void closeFactory(@Disposes OwfsConnectionFactory factory) {
		factory = null;
	}

}
