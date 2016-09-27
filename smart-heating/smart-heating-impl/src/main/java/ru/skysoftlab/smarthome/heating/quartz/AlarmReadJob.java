package ru.skysoftlab.smarthome.heating.quartz;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobExecutionContext;
import org.apache.openejb.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner;
import ru.skysoftlab.smarthome.heating.onewire.IAlarmScannerJob;
import ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig;

/**
 * Задание на сканирование датчиков.
 * 
 * @author Loktionov Artem
 *
 */
@Singleton
public class AlarmReadJob implements Job, IAlarmScannerJob {

	private static final long serialVersionUID = -2990505287440980547L;

	private Logger LOG = LoggerFactory.getLogger(AlarmReadJob.class);

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Inject
	private IAlarmDevScanner scanner;

	// @Inject
	// private ITempAlarmHandler tempAlarmHandler;
	//
	// @Inject
	// private AlarmingDevicesReader reader;
	//
	// private List<IDs18bConfig> errorAddHandlerList;

	@PostConstruct
	public void init() {
		for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
			// setAlarmingDeviceHandler(config);
			scanner.setDeviceConfig(config);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// setErrorAlarmingDeviceHandlers();
		LOG.info("Сканирование датчиков в сигнализации " + context.getJobDetail());
		scanner.scanAlarmingDevices();
		// reader.run();
	}

	// @Override
	// public void setAlarmingDeviceHandler(IDs18bConfig config) {
	// // try {
	// // addAlarmingDeviceHandler(config);
	// // } catch (IOException | OwfsException e) {
	// // addErrorHandler(config);
	// // LOG.error("Error add AlarmingDeviceHandler " + config, e);
	// // }
	// }

	// @Override
	// public boolean isAlarmingDeviceHandlerInstalled(String deviceName) {
	// return reader.isAlarmingDeviceHandlerInstalled(deviceName);
	// }

	@PreDestroy
	public void deInit() {
		scanner = null;
	}

	@Override
	public void setDeviceConfig(IDs18bConfig config) {
		scanner.setDeviceConfig(config);
	}

	// /**
	// * Добавляет обработчик.
	// *
	// * @param config
	// * @throws IOException
	// * @throws OwfsException
	// */
	// private void addAlarmingDeviceHandler(IDs18bConfig config) throws
	// IOException, OwfsException {
	// if (reader.isAlarmingDeviceHandlerInstalled(config.getDeviceName())) {
	// reader.removeAlarmingDeviceHandler(config.getDeviceName());
	// LOG.info("Remove handler " + config);
	// }
	// reader.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(config,
	// tempAlarmHandler));
	// LOG.info("Add handler " + config);
	// }
	//
	// /**
	// * Добавляет конфиг в список ошибок.
	// *
	// * @param config
	// */
	// private void addErrorHandler(IDs18bConfig config) {
	// if (errorAddHandlerList == null) {
	// errorAddHandlerList = new ArrayList<>();
	// }
	// errorAddHandlerList.add(config);
	// }
	//
	// /**
	// * Добавляет обработчики из списка ошибок.
	// */
	// private void setErrorAlarmingDeviceHandlers() {
	// if (errorAddHandlerList != null) {
	// for (int i = 0; i < errorAddHandlerList.size(); i++) {
	// IDs18bConfig config = errorAddHandlerList.get(i);
	// try {
	// addAlarmingDeviceHandler(config);
	// errorAddHandlerList.remove(i);
	// } catch (IOException | OwfsException e) {
	// LOG.error("Error add AlarmingDeviceHandler " + config, e);
	// }
	// }
	// if (errorAddHandlerList.isEmpty()) {
	// errorAddHandlerList = null;
	// }
	// }
	// }

}
