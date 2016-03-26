package ru.skysoftlab.smarthome.heating.quartz;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobExecutionContext;
import org.apache.openejb.quartz.JobExecutionException;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.alarm.AlarmingDevicesReader;

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
//@ApplicationScoped
//@Stateful
@Singleton
public class AlarmReadJob implements Job, IAlarmScanner {

	private static final long serialVersionUID = -2990505287440980547L;

	@Inject
	private OwfsConnectionFactory factory;

	private AlarmingDevicesReader reader;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Inject
	private ITempAlarmHandler tempAlarmHandler;
	
	public AlarmReadJob() {
		System.out.println("!!!!!-------------!!!!!!!!!!!!!!");
	}

	@PostConstruct
	public void init() {
		reader = new AlarmingDevicesReader(factory);
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
		reader.run();
		System.out.println("Сканирование");
	}

	public void setAlarmingDeviceHandler(IDs18bConfig config) throws IOException, OwfsException {
		if (reader.isAlarmingDeviceHandlerInstalled(config.getDeviceName())) {
			reader.removeAlarmingDeviceHandler(config.getDeviceName());
		}
		reader.addAlarmingDeviceHandler(new Ds18bAlarmingDeviceListener(config, tempAlarmHandler));
	}

//	@Override
//	public void setInterval(int interval) {
//		// TODO убрать из интерфейса
//	}

	@PreDestroy
	public void deInit() {
		reader = null;
	}

}
