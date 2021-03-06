package ru.skysoftlab.smarthome.heating.quartz;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobExecutionContext;
import org.apache.openejb.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Temp;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

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

	@Resource
	private UserTransaction utx;

	@Inject
	private SensorsAndDevicesProvider sensorsProvider;
	
	@Inject
	private IOneWire client;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOG.info("Сканирование температур " + context.getJobDetail());
		Date now = context.getScheduledFireTime();
		for (Sensor sensor : sensorsProvider.getDs18bConfigs()) {
			try {
				float t = round(client.getTemperature(sensor.getSensorId()), 1);
				try {
					utx.begin();
					em.persist(new Temp(t, sensor, now));
					utx.commit();
				} catch (Exception e) {
					LOG.error("Save temp error", e);
				}
			} catch (IOException e) {
				LOG.error("Read sensor(" + sensor.toLog() + ") error", e);
			}
		}
	}

	/**
	 * Округлить.
	 * 
	 * @param number
	 * @param scale
	 * @return
	 */
	private float round(float number, int scale) {
		int pow = 10;
		for (int i = 1; i < scale; i++)
			pow *= 10;
		float tmp = number * pow;
		return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
	}

}
