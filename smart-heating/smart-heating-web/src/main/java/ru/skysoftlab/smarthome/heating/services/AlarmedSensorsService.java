package ru.skysoftlab.smarthome.heating.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import ru.skysoftlab.smarthome.heating.ejb.EmProducer;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
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

	private EntityManager em;

	public AlarmedSensorsService() {
		try {
			em = lookupBean(EmProducer.class).getEM();
		} catch (Exception e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

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

	private OwfsConnectionConfig getOwfsConfig() {
		OwfsConnectionConfig rv = null;
		String url;
		try {
			url = (String) (new InitialContext())
					.lookup("java:comp/env/owfsServerUrl");
			String[] urlParams = url.split(":");
			rv = new OwfsConnectionConfig(urlParams[0],
					Integer.valueOf(urlParams[1]));
		} catch (NamingException e) {
			e.printStackTrace();
			rv = new OwfsConnectionConfig("localhost", 3000);
		}
		return rv;
	}

	// TODO переделать создание коннекции
	public Collection<AlarmedSensorDto> findAll() {
		Collection<AlarmedSensorDto> rv = new ArrayList<>();
		OwfsConnection client = OwfsConnectionFactory
				.newOwfsClient(getOwfsConfig());
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

	@SuppressWarnings("unchecked")
	protected <B> B lookupBean(Class<B> beanClass) throws NamingException {
		return (B) new InitialContext().lookup("java:module/"
				+ beanClass.getSimpleName());
	}

}
