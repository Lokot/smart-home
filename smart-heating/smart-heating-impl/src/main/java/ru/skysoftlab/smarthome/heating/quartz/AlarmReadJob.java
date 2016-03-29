package ru.skysoftlab.smarthome.heating.quartz;

import java.io.IOException;

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
 * @author Артём
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

	@PostConstruct
	public void init() {
		try {
			for (IDs18bConfig config : sensorsProvider.getDs18bConfigs()) {
				setAlarmingDeviceHandler(config);
			}
		} catch (IOException | OwfsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Сканирование датчиков в сигнализации" + context.getJobDetail());
		reader.run();
	}

	public void setAlarmingDeviceHandler(IDs18bConfig config) throws IOException, OwfsException {
		if (reader.isAlarmingDeviceHandlerInstalled(config.getDeviceName())) {
			reader.removeAlarmingDeviceHandler(config.getDeviceName());
			LOG.info("Remve handler " + config);
		}
		reader.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(config, tempAlarmHandler));
		LOG.info("Add handler " + config);
	}

	@PreDestroy
	public void deInit() {
		reader = null;
	}

}
