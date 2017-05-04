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
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
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

	@Inject
	private IOneWire client;

	@Inject
	private SensorsAndDevicesProvider sensorsProvider;

	public Collection<AlarmedSensorDto> findAlarmed() {
		Collection<AlarmedSensorDto> rv = new ArrayList<>();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		return rv;
	}

	// TODO переделать создание коннекции
	public Collection<TemperatureDto> findTemperatures() {
		Collection<TemperatureDto> rv = new ArrayList<>();
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
			return client.getIdsDS18B();
		} catch (IOException e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		return new ArrayList<String>();
	}

}
