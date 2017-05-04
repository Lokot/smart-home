package ru.skysoftlab.smarthome.heating.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
import ru.skysoftlab.smarthome.heating.entitys.Valve;

/**
 * Провайдер доступа к настройкам датчиков.
 * 
 * @author Артём
 *
 */
public class SensorsAndDevicesProvider implements Serializable {

	private static final long serialVersionUID = 3004034316446904991L;

	@Inject
	private EntityManager em;

	@Resource
	private UserTransaction utx;

	/**
	 * Возвращает настройки датчика.
	 * 
	 * @param id
	 *            1-ware id
	 * @return настройки датчика
	 */
	public Sensor getDs18bConfig(String id) {
		try {
			Sensor rv;
			try {
				utx.begin();
				CriteriaBuilder builder = em.getCriteriaBuilder();
				CriteriaQuery<Sensor> criteriaQuery = builder
						.createQuery(Sensor.class);
				Root<Sensor> s = criteriaQuery.from(Sensor.class);
				criteriaQuery.select(s).where(
						builder.equal(s.get(Sensor_.sensorId), id));
				TypedQuery<Sensor> query = em.createQuery(criteriaQuery);
				try {
					rv = query.getSingleResult();
					rv.getMaster().getDef();
					rv.getMaster().getSensors();
					rv.getGpioPin();
				} catch (NoResultException e) {
					return null;
				}
				utx.commit();
				return rv;
			} finally {
				if (utx.getStatus() == 0) {
					utx.rollback();
				}
			}
		} catch (Exception e) {
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
	public Collection<Boiler> getAllBoilers() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Boiler> criteriaQuery = builder.createQuery(Boiler.class);
		Root<Boiler> s = criteriaQuery.from(Boiler.class);
		criteriaQuery.select(s);
		TypedQuery<Boiler> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * Возвращает пины контуров.
	 * 
	 * @return
	 */
	public Collection<Valve> getAllKonturs() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Valve> criteriaQuery = builder.createQuery(Valve.class);
		Root<Valve> s = criteriaQuery.from(Valve.class);
		criteriaQuery.select(s);
		TypedQuery<Valve> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * Возвращает все пины.
	 * 
	 * @return
	 */
	public List<Device> getAllDevices() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Device> criteriaQuery = builder.createQuery(Device.class);
		Root<Device> s = criteriaQuery.from(Device.class);
		criteriaQuery.select(s);
		TypedQuery<Device> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * Возвращает не закрепленные датчики.
	 * 
	 * @return
	 */
	public Collection<Sensor> getFreeSensors() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Sensor> criteriaQuery = builder.createQuery(Sensor.class);
		Root<Sensor> s = criteriaQuery.from(Sensor.class);
		criteriaQuery.select(s).where(s.get(Sensor_.master).isNull());
		TypedQuery<Sensor> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}
}
