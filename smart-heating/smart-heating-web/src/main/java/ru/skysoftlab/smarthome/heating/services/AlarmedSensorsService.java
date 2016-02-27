package ru.skysoftlab.smarthome.heating.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;

import ru.skysoftlab.smarthome.heating.dto.AlarmedSensorDto;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Сервис для работы с датчиками в сигнализации.
 * 
 * @author Артём
 *
 */
@UIScoped
public class AlarmedSensorsService implements Serializable {

	private static final long serialVersionUID = -620019230850598972L;

	@Inject
	private EntityManager em;
	
	@Inject
	private OwfsConnectionConfig config;

	// TODO перекинуть (повторяется в GpioController)
	private Sensor getDs18bConfig(String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Sensor> criteriaQuery = builder.createQuery(Sensor.class);
		Root<Sensor> s = criteriaQuery.from(Sensor.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(Sensor_.sensorId), id));
		TypedQuery<Sensor> query = em.createQuery(criteriaQuery);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

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
				Sensor sensor = getDs18bConfig(sensorId);
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
