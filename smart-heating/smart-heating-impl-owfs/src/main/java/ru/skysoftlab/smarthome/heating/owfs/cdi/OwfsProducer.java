package ru.skysoftlab.smarthome.heating.owfs.cdi;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.alarm.AlarmingDevicesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.annatations.AppProperty;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.OWFS_SERVER;

/**
 * Продюсер для работы с OneWare сетью.
 * 
 * @author Артём
 *
 */
public class OwfsProducer implements Serializable {

	private static final long serialVersionUID = 7565939967506122425L;

	private Logger LOG = LoggerFactory.getLogger(OwfsProducer.class);

	// @Inject
	// @AppProperty(OWFS_SERVER)
	// private String url;

	// @Inject
	// @AppProperty(JobController.INTERVAL)
	// private Integer interval;

	@Produces
	public OwfsConnectionConfig getOwfsConnectionConfig(@AppProperty(OWFS_SERVER) String url) {
		OwfsConnectionConfig rv = null;
		String[] urlParams = url.split(":");
		rv = new OwfsConnectionConfig(urlParams[0], Integer.valueOf(urlParams[1]));
		return rv;
	}

	@Produces
	public OwfsConnectionFactory getOwfsConnectionFactory(@AppProperty(OWFS_SERVER) String url) {
		String[] urlParams = url.split(":");
		OwfsConnectionFactory owfsConnectionFactory = new OwfsConnectionFactory(urlParams[0],
				Integer.valueOf(urlParams[1]));
		return owfsConnectionFactory;
	}

	@Produces
	public AlarmingDevicesReader getAlarmingDevicesReader(OwfsConnectionFactory factory) {
		return new AlarmingDevicesReader(factory);
	}

	@Produces
	@RequestScoped
	public OwfsConnection getOwfsConnectionConfig(OwfsConnectionConfig config) {
		return OwfsConnectionFactory.newOwfsClient(config);
	}

	public void deInitClient(@Disposes OwfsConnection client) {
		try {
			client.disconnect();
			LOG.info("OWSF-connection closed");
		} catch (IOException e) {
			LOG.error("Close OWSF-connection error", e);
		}
		client = null;
	}

}
