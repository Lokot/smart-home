package ru.skysoftlab.smarthome.heating.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.dto.AlarmedSensorDto;
import ru.skysoftlab.smarthome.heating.dto.TemperatureDto;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Сервис для работы с датчиками.
 * 
 * @author Артём
 *
 */
// TODO проверить работоспособность
@RequestScoped
public class SensorsService implements Serializable {

	private static final long serialVersionUID = -620019230850598972L;

//	@Inject
//	private OwfsConnectionConfig config;
	
//	@Inject
//	private OwfsConnection client;
	
	@Inject
	private IOneWire client;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	// TODO переделать создание коннекции
	public Collection<AlarmedSensorDto> findAlarmed() {
		Collection<AlarmedSensorDto> rv = new ArrayList<>();
//		OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
		try {
			Map<String, Float> alarmedTemps = client.getAlarmedTemps();
			for (String sensorId : alarmedTemps.keySet()) {
				AlarmedSensorDto dto = new AlarmedSensorDto();
				dto.setSensorId(sensorId);
				dto.setFastTemp(alarmedTemps.get(sensorId));
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
//			List<String> alarmedSensorsIds = client.getAlarmed(); // OwsfUtilDS18B.getAlarmed(client);
//			for (String sensorId : alarmedSensorsIds) {
//				AlarmedSensorDto dto = new AlarmedSensorDto();
//				dto.setSensorId(sensorId);
////				dto.setFastTemp(OwsfUtilDS18B.getFasttemp(client, sensorId));
//				dto.setFastTemp(client.getFasttemp(sensorId));
//				Sensor sensor = sensorsProvider.getDs18bConfig(sensorId);
//				if (sensor != null) {
//					dto.setName(sensor.getName());
//					dto.setLow(sensor.getLow());
//					dto.setTop(sensor.getTop());
//				} else {
//					dto.setName("Не задан");
//				}
//				rv.add(dto);
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} finally {
//			if (client != null) {
//				try {
//					client.disconnect();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
//				}
//			}
		}
		return rv;
	}

	// TODO переделать создание коннекции
	public Collection<TemperatureDto> findTemperatures() {
		Collection<TemperatureDto> rv = new ArrayList<>();
//		OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
		try {
			for (Sensor sensor : sensorsProvider.getDs18bConfigs()) {
				TemperatureDto dto = new TemperatureDto();
				dto.setSensorName(sensor.getName());
				dto.setTemp(client.getFasttemp(sensor.getSensorId()));
				rv.add(dto);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} finally {
//			if (client != null) {
//				try {
//					client.disconnect();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
//				}
//			}
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
//			OwfsConnection client = OwfsConnectionFactory.newOwfsClient(config);
			return client.getIdsDS18B();
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		return new ArrayList<String>();
	}

}
