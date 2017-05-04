package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

/**
 * Реализация интерфейса взаимодействия с 1-Wire сетью через OneWire File System
 * Server (owserver).
 * 
 * @author Артём
 *
 */
public class OwfsOneWire implements IOneWire {

	private static final long serialVersionUID = -4880078481700726553L;
	
	private Logger LOG = LoggerFactory.getLogger(OwfsOneWire.class);

	@Inject
	private OwfsConnection client;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IOneWire#getTemperature(java.
	 * lang.String)
	 */
	@Override
	public Float getTemperature(String id) throws IOException {
		try {
			return OwsfUtilDS18B.getTemperature(client, id);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IOneWire#setHighTemp(java.lang
	 * .String, java.lang.Float)
	 */
	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		try {
			client.write(String.format("/%s/temphigh", id), temp.toString());
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IOneWire#setLowTemp(java.lang
	 * .String, java.lang.Float)
	 */
	@Override
	public void setLowTemp(String id, Float temp) throws IOException {
		try {
			client.write(String.format("/%s/templow", id), temp.toString());
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IOneWire#getFasttemp(java.lang
	 * .String)
	 */
	@Override
	public Float getFasttemp(String id) throws IOException {
		try {
			return OwsfUtilDS18B.getFasttemp(client, id);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.onewire.IOneWire#getIdsDS18B()
	 */
	@Override
	public List<String> getIdsDS18B() throws IOException {
		try {
			return OwsfUtilDS18B.getIdsDS18B(client);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.onewire.IOneWire#getAlarmed()
	 */
	@Override
	public List<String> getAlarmed() throws IOException {
		try {
			return OwsfUtilDS18B.getAlarmed(client);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.onewire.IOneWire#getAlarmedTemps()
	 */
	@Override
	public Map<String, Float> getAlarmedTemps() throws IOException {
		Map<String, Float> rv = new HashMap<>();
		try {
			List<String> alarmedIds = OwsfUtilDS18B.getAlarmed(client);
			for (String id : alarmedIds) {
				try {
					rv.put(id, getFasttemp(id));
				} catch (IOException e1) {
					rv.put(id, null);
				}
			}
			return rv;
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void close() throws IOException {
		try {
			client.disconnect();
			LOG.info("OWSF-connection closed");
		} catch (IOException e) {
			LOG.error("Close OWSF-connection error", e);
		}		
	}

}
