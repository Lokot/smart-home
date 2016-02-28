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
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Сервис для работы с датчиками в сигнализации.
 * 
 * @author Артём
 *
 */
public class AlarmedSensorsService implements Serializable {

	private static final long serialVersionUID = -620019230850598972L;

	@Inject
	private OwfsConnectionConfig config;
	
	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	// TODO переделать создание коннекции
	public Collection<AlarmedSensorDto> findAll() {
		Collection<AlarmedSensorDto> rv = new ArrayList<>();
		OwfsConnection client = OwfsConnectionFactory
				.newOwfsClient(config);
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

}
