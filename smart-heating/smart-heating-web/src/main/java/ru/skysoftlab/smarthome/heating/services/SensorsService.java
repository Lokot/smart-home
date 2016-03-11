package ru.skysoftlab.smarthome.heating.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;

import ru.skysoftlab.smarthome.heating.dto.AlarmedSensorDto;
import ru.skysoftlab.smarthome.heating.dto.TemperatureDto;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Сервис для работы с датчиками.
 * 
 * @author Артём
 *
 */
public class SensorsService implements Serializable {

	private static final long serialVersionUID = -620019230850598972L;

	@Inject
	private OwfsConnectionConfig config;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	// TODO переделать создание коннекции
	public Collection<AlarmedSensorDto> findAlarmed() {
		Collection<AlarmedSensorDto> rv = new ArrayList<>();
		OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
		try {
			List<String> alarmedSensorsIds = OwsfUtilDS18B.getAlarmed(client);
			for (String sensorId : alarmedSensorsIds) {
				AlarmedSensorDto dto = new AlarmedSensorDto();
				dto.setSensorId(sensorId);
				dto.setFastTemp(OwsfUtilDS18B.getFasttemp(client, sensorId));
				Sensor sensor = sensorsProvider.getDs18bConfig(sensorId);
				if (sensor != null) {
					dto.setName(sensor.getName());
					dto.setLow(sensor.getLow());
					dto.setTop(sensor.getTop());
				} else {
					dto.setName("Не задан");
				}
				rv.add(dto);
			}
		} catch (OwfsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
				}
			}
		}
		return rv;
	}

	// TODO переделать создание коннекции
	public Collection<TemperatureDto> findTemperatures() {
		Collection<TemperatureDto> rv = new ArrayList<>();
		OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
		try {
			for (Sensor sensor : sensorsProvider.getDs18bConfigs()) {
				TemperatureDto dto = new TemperatureDto();
				dto.setSensorName(sensor.getName());
				dto.setTemp(OwsfUtilDS18B.getFasttemp(client,
						sensor.getSensorId()));
				rv.add(dto);
			}
		} catch (OwfsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
				}
			}
		}
		return rv;
	}

	/**
	 * Возвращает список датчиков температур.
	 * 
	 * @return
	 */
	public List<String> getIdsDS18B() {
		try {
			OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
			return OwsfUtilDS18B.getIdsDS18B(client);
		} catch (OwfsException | IOException e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		return new ArrayList<String>();
	}

}
