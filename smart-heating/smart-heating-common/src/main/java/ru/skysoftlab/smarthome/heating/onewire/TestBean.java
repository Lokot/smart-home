package ru.skysoftlab.smarthome.heating.onewire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.Enums.OwBusReturn;
import org.owfs.jowfsclient.Enums.OwPersistence;
import org.owfs.jowfsclient.Enums.OwTemperatureScale;
import org.owfs.jowfsclient.OwfsException;
import org.owfs.jowfsclient.util.OWFSUtils;

public class TestBean {

	public static void main(String[] args) {
		try {
			OwfsConnectionConfig connectionConfig = new OwfsConnectionConfig(
					"192.168.0.86", 3000);
			connectionConfig.setPersistence(OwPersistence.ON);
			connectionConfig.setTemperatureScale(OwTemperatureScale.CELSIUS);
			connectionConfig.setBusReturn(OwBusReturn.ON);
			OwfsConnection client = OwfsConnectionFactory
					.newOwfsClient(connectionConfig);

			 for (String path : getIdsDS18B(client)) {
			 System.out.println(getTemperature(client, path));
			 }

			// owfs -u /mnt/1wire/

			// System.out.println(client.read("/81.949433000000/type"));
			// for (String ttt : client.listDirectoryAll("/alarm")) {
			// printList(client.listDirectoryAll(ttt));
			// }
			//
			// [
			// /alarm/28.F4E330040000/address,
			// /alarm/28.F4E330040000/alias,
			// /alarm/28.F4E330040000/crc8,
			// /alarm/28.F4E330040000/errata,
			// /alarm/28.F4E330040000/family,
			// /alarm/28.F4E330040000/fasttemp,
			// /alarm/28.F4E330040000/id,
			// /alarm/28.F4E330040000/locator,
			// /alarm/28.F4E330040000/power,
			// /alarm/28.F4E330040000/r_address,
			// /alarm/28.F4E330040000/r_id,
			// /alarm/28.F4E330040000/r_locator,
			// /alarm/28.F4E330040000/temperature,
			// /alarm/28.F4E330040000/temperature10,
			// /alarm/28.F4E330040000/temperature11,
			// /alarm/28.F4E330040000/temperature12,
			// /alarm/28.F4E330040000/temperature9,
			// /alarm/28.F4E330040000/temphigh,
			// /alarm/28.F4E330040000/templow,
			// /alarm/28.F4E330040000/type]

			// TODO что такое fasttemp

//			System.out.println(client.read("/28.F4E330040000/fasttemp"));
//			System.out.println(client.read("/28.F4E330040000/temperature"));
//			System.out.println(client
//					.read("/alarm/28.F4E330040000/temperature"));

			// client.write("/28.8AF530040000/templow", "10");//
			// read("/alarm/28.F4E330040000/temphigh")
//			client.write("/28.F4E330040000/templow", "5");
//			client.write("/28.76E830040000/templow", "70");
			// System.out.println("1111111111111111111");
			getAlarmed(client);
			// for (String ttt : client.listDirectoryAll("/alarm")) {
			// printList(client.listDirectoryAll(ttt));
			// }

			// System.out.println(client.read("/alarm/28.F4E330040000/temphigh"));
			// client.write(path, dataToWrite);

			client.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setTemphigh(OwfsConnection client, String id, Float temp) throws IOException, OwfsException {
		client.write("/" + id + "/temphigh", temp.toString());
	}

	public static void setTemplow(OwfsConnection client, String id, Float temp) throws IOException, OwfsException {
		client.write("/" + id + "/templow", temp.toString());
	}

	public static String getFasttemp(OwfsConnection client, String id)
			throws IOException, OwfsException {
		return client.read("/" + id + "/fasttemp");
	}

	public static List<String> getIdsDS18B(OwfsConnection client)
			throws OwfsException, IOException {
		List<String> rv = new ArrayList<>();
		for (String path : client.listDirectoryAll("/")) {
			try {
				if (client.read(path + "/type").equals("DS18B20")) {
					System.out.println(OWFSUtils
							.extractDeviceNameFromDevicePath(path));
					rv.add(OWFSUtils.extractDeviceNameFromDevicePath(path));
				}
			} catch (Throwable r) {

			}
		}
		return rv;
	}

	public static List<String> getAlarmed(OwfsConnection client)
			throws OwfsException, IOException {
		List<String> rv = new ArrayList<>();
		for (String path : client.listDirectoryAll("/alarm")) {
			try {
				if (client.read(path + "/type").equals("DS18B20")) {
					System.out.println(OWFSUtils
							.extractDeviceNameFromDevicePath(path));
					rv.add(OWFSUtils.extractDeviceNameFromDevicePath(path));
				}
			} catch (Throwable r) {

			}
		}
		return rv;
	}

	public static String getTemperature(OwfsConnection client, String id)
			throws OwfsException, IOException {
		String tempPath = "/" + id + "/temperature";
		// if(client.exists(tempPath)){
		return client.read(tempPath);
		// }
		// throw new OwfsException("Нет датчика " + tempPath, 1);
	}

	public static void printList(List<String> list) {
		for (String ttt : list) {
			System.out.println(ttt);
		}
	}

}
