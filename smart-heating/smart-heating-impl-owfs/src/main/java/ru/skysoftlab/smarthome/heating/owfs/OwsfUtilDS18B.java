package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.util.OWFSUtils;

/**
 * Утилитный класс для работы с датчиками DS18B20 через OneWire File System
 * Server (owserver).
 * 
 * @author Артём
 *
 */
public class OwsfUtilDS18B {

	private final static String TYPE = "DS18B20";

	/**
	 * Устанавливает максимальную температуру.
	 * 
	 * @param client
	 * @param id
	 * @param temp
	 * @throws IOException
	 * @throws OwfsException
	 */
	public static void setTemphigh(OwfsConnection client, String id, Float temp) throws IOException, OwfsException {
		client.write(String.format("/%s/temphigh", id), temp.toString());
	}

	/**
	 * Устанавливает минимальную температуру.
	 * 
	 * @param client
	 * @param id
	 * @param temp
	 * @throws IOException
	 * @throws OwfsException
	 */
	public static void setTemplow(OwfsConnection client, String id, Float temp) throws IOException, OwfsException {
		client.write(String.format("/%s/templow", id), temp.toString());
	}

	/**
	 * Возвращает быструю температуру.
	 * 
	 * @param client
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws OwfsException
	 */
	public static Float getFasttemp(OwfsConnection client, String id) throws IOException, OwfsException {
		return Float.parseFloat(client.read(String.format("/%s/fasttemp", id)));
	}

	/**
	 * Возвращает список датчиков.
	 * 
	 * @param client
	 * @return
	 * @throws OwfsException
	 * @throws IOException
	 */
	public static List<String> getIdsDS18B(OwfsConnection client) throws OwfsException, IOException {
		List<String> rv = new ArrayList<>();
		for (String path : client.listDirectoryAll("/")) {
			try {
				if (client.read(path + "/type").equals(TYPE)) {
					rv.add(OWFSUtils.extractDeviceNameFromDevicePath(path));
				}
			} catch (Throwable r) {

			}
		}
		return rv;
	}

	/**
	 * Возвращает список сигнализирующих датчиков.
	 * 
	 * @param client
	 * @return
	 * @throws OwfsException
	 * @throws IOException
	 */
	public static List<String> getAlarmed(OwfsConnection client) throws OwfsException, IOException {
		List<String> rv = new ArrayList<>();
		for (String path : client.listDirectoryAll("/alarm")) {
			try {
				if (client.read(path + "/type").equals(TYPE)) {
					rv.add(OWFSUtils.extractDeviceNameFromDevicePath(path));
				}
			} catch (Throwable r) {

			}
		}
		return rv;
	}

	/**
	 * Возвращает точную температуру.
	 * 
	 * @param client
	 * @param id
	 * @return
	 * @throws OwfsException
	 * @throws IOException
	 */
	public static Float getTemperature(OwfsConnection client, String id) throws OwfsException, IOException {
		return Float.parseFloat(client.read(String.format("/%s/temperature", id)));
	}
}
