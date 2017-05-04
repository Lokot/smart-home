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

import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
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
	private SensorsAndDevicesProvider sensorsProvider;

	@Inject
	private IAlarmDevScanner scanner;

	@PostConstruct
	public void init() {
		for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
			scanner.setDeviceConfig(config);
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Сканирование датчиков в сигнализации " + context.getJobDetail());
		scanner.scanAlarmingDevices();
	}

	@PreDestroy
	public void deInit() {
		scanner = null;
	}

	@Override
	public void setDeviceConfig(IDs18bConfig config) {
		scanner.setDeviceConfig(config);
	}

}
