package ru.skysoftlab.smarthome.heating.quartz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobExecutionContext;
import org.apache.openejb.quartz.JobExecutionException;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.owfs.Ds18bAlarmingDeviceListener;
import ru.skysoftlab.smarthome.heating.owfs.IAlarmScanner;
import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler;

/**
 * Задание на сканирование датчиков.
 * 
 * @author Loktionov Artem
 *
 */
@Singleton
public class AlarmReadJob implements Job, IAlarmScanner {

	private static final long serialVersionUID = -2990505287440980547L;

	private Logger LOG = LoggerFactory.getLogger(AlarmReadJob.class);

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Inject
	private ITempAlarmHandler tempAlarmHandler;

	@Inject
	private AlarmingDevicesReader reader;

	private List<IDs18bConfig> errorAddHandlerList;

	@PostConstruct
	public void init() {
		for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
			setAlarmingDeviceHandler(config);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		setErrorAlarmingDeviceHandlers();
		LOG.info("Сканирование датчиков в сигнализации " + context.getJobDetail());
		reader.run();
	}

	@Override
	public void setAlarmingDeviceHandler(IDs18bConfig config) {
		try {
			addAlarmingDeviceHandler(config);
		} catch (IOException | OwfsException e) {
			addErrorHandler(config);
			LOG.error("Error add AlarmingDeviceHandler " + config, e);
		}
	}

	@Override
	public boolean isAlarmingDeviceHandlerInstalled(String deviceName) {
		return reader.isAlarmingDeviceHandlerInstalled(deviceName);
	}

	@PreDestroy
	public void deInit() {
		reader = null;
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
