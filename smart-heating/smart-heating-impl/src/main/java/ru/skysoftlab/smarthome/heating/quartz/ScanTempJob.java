package ru.skysoftlab.smarthome.heating.quartz;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobExecutionContext;
import org.apache.openejb.quartz.JobExecutionException;
import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Temp;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

/**
 * Задание на сканирование температуры.
 * 
 * @author Локтионов А.Г.
 *
 */
@Singleton
public class ScanTempJob implements Job {

	private Logger LOG = LoggerFactory.getLogger(ScanTempJob.class);

	@Inject
	private EntityManager em;

	@Inject
	private OwfsConnectionFactory factory;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Сканирование температур" + context.getJobDetail());
		Date now = context.getTrigger().getStartTime();
		OwfsConnection client = factory.createNewConnection();
		for (Sensor sensor : sensorsProvider.getDs18bConfigs()) {
			try {
				float t = OwsfUtilDS18B.getTemperature(client, sensor.getSensorId());
				try {
					em.persist(new Temp(t, sensor, now));
				} catch (Exception e) {
					LOG.error("Save temp error", e);
				}
			} catch (OwfsException | IOException e) {
				LOG.error("Read sensor(" + sensor.toLog() + ") error", e);
			}
		}
		try {
			client.disconnect();
		} catch (IOException e) {
			LOG.error("Close connection error", e);
		}
		client = null;
	}

}
