package ru.skysoftlab.smarthome.heating.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.devices.IDevice;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin_;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;

/**
 * Провайдер доступа к настройкам датчиков.
 * 
 * @author Артём
 *
 */
public class SensorsAndGpioProvider implements Serializable {

	private static final long serialVersionUID = 3004034316446904991L;
	
	@Inject
	private EntityManager em;

	/**
	 * Возвращает настройки датчика.
	 * 
	 * @param id
	 *            1-ware id
	 * @return настройки датчика
	 */
	public Sensor getDs18bConfig(String id) {
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

	/**
	 * Возвращает коллекцию датчиков.
	 * 
	 * @return
	 */
	public Collection<Sensor> getDs18bConfigs() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Sensor> criteriaQuery = builder.createQuery(Sensor.class);
		Root<Sensor> s = criteriaQuery.from(Sensor.class);
		criteriaQuery.select(s);
		TypedQuery<Sensor> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * Возвращает пин котла.
	 * 
	 * @return
	 */
	public IDevice getBoilerGpioPin() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(GpioPin_.type), DeviceType.BOILER));
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		GpioPin gpioPin = query.getSingleResult();
		return gpioPin;
	}

	/**
	 * Возвращает пины контуров.
	 * 
	 * @return
	 */
	public Collection<? extends IDevice> getAllKonturs() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(GpioPin_.type), DeviceType.KONTUR));
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		List<GpioPin> rv = query.getResultList();
		return rv;
	}

	/**
	 * Возвращает все пины.
	 * 
	 * @return
	 */
	public List<GpioPin> getAllGpioPins() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s);
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		List<GpioPin> rv = query.getResultList();
		return rv;
	}
}
