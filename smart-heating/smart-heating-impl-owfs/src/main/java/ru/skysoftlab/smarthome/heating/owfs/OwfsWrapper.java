package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;

import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

public class OwfsWrapper implements IOneWire {

	private static final long serialVersionUID = -4880078481700726553L;
	
	@Inject
	private OwfsConnection client;

	@Override
	public Float getTemperature(String id) throws IOException {
		try {
			return OwsfUtilDS18B.getTemperature(client, id);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		try {
			client.write(String.format("/%s/temphigh", id), temp.toString());
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void setLowTemp(String id, Float temp) throws IOException {
		try {
			client.write(String.format("/%s/templow", id), temp.toString());
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public Float getFasttemp(String id) throws IOException {
		try {
			return OwsfUtilDS18B.getFasttemp(client, id);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<String> getIdsDS18B() throws IOException {
		try {
			return OwsfUtilDS18B.getIdsDS18B(client);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<String> getAlarmed() throws IOException {
		try {
			return OwsfUtilDS18B.getAlarmed(client);
		} catch (OwfsException e) {
			throw new IOException(e);
		}
	}

}
