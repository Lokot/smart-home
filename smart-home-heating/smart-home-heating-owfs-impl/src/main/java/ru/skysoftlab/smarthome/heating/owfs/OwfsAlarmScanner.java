package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner;
import ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.onewire.ITempAlarmHandler;

/**
 * Сканер датчиков для OWFS-сервера.
 * 
 * @author Артём
 *
 */
public class OwfsAlarmScanner implements IAlarmDevScanner {

	private static final long serialVersionUID = -1065714883620629842L;

	private Logger LOG = LoggerFactory.getLogger(OwfsAlarmScanner.class);

	@Inject
	private ITempAlarmHandler tempAlarmHandler;

	@Inject
	private AlarmingDevicesReader reader;

	private List<IDs18bConfig> errorAddHandlerList;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#scanAlarmingDevices
	 * ()
	 */
	@Override
	public void scanAlarmingDevices() {
		setErrorAlarmingDeviceHandlers();
		reader.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#setDeviceConfig
	 * (ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig)
	 */
	@Override
	public void setDeviceConfig(IDs18bConfig config) {
		try {
			addAlarmingDeviceHandler(config);
		} catch (IOException | OwfsException e) {
			addErrorHandler(config);
			LOG.error("Error add AlarmingDeviceHandler " + config, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#setDeviceConfigs
	 * (java.util.List)
	 */
	@Override
	public void setDeviceConfigs(List<IDs18bConfig> configs) {
		for (IDs18bConfig config : configs) {
			setDeviceConfig(config);
		}
	}

	/**
	 * Добавляет обработчик.
	 * 
	 * @param config
	 * @throws IOException
	 * @throws OwfsException
	 */
	private void addAlarmingDeviceHandler(IDs18bConfig config) throws IOException, OwfsException {
		if (reader.isAlarmingDeviceHandlerInstalled(config.getDeviceName())) {
			reader.removeAlarmingDeviceHandler(config.getDeviceName());
			LOG.info("Remove handler " + config);
		}
		reader.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(config, tempAlarmHandler));
		LOG.info("Add handler " + config);
	}

	/**
	 * Добавляет конфиг в список ошибок.
	 * 
	 * @param config
	 */
	private void addErrorHandler(IDs18bConfig config) {
		if (errorAddHandlerList == null) {
			errorAddHandlerList = new ArrayList<>();
		}
		errorAddHandlerList.add(config);
	}

	/**
	 * Добавляет обработчики из списка ошибок.
	 */
	private void setErrorAlarmingDeviceHandlers() {
		if (errorAddHandlerList != null) {
			for (int i = 0; i < errorAddHandlerList.size(); i++) {
				IDs18bConfig config = errorAddHandlerList.get(i);
				try {
					addAlarmingDeviceHandler(config);
					errorAddHandlerList.remove(i);
				} catch (IOException | OwfsException e) {
					LOG.error("Error add AlarmingDeviceHandler " + config, e);
				}
			}
			if (errorAddHandlerList.isEmpty()) {
				errorAddHandlerList = null;
			}
		}
	}

}
