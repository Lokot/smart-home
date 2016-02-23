package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin_;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;
import ru.skysoftlab.smarthome.heating.gpio.IGpioController;
import ru.skysoftlab.smarthome.heating.gpio.IGpioPin;
import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.util.PinsUtil;

/**
 * Управляет устройствами.
 * 
 * @author Артём
 *
 */
public class GpioController implements IGpioController {

	private EntityManager em;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpen(java.lang
	 * .String)
	 */
	@Override
	public void toOpen(String deviceName) {
		for (IGpioPin gpioPin : getDs18bConfig(deviceName).getGpioPin()) {
			try {
				open(gpioPin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toClose(java.lang
	 * .String)
	 */
	@Override
	public void toClose(String deviceName) {
		for (IGpioPin gpioPin : getDs18bConfig(deviceName).getGpioPin()) {
			try {
				close(gpioPin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOn()
	 */
	@Override
	public void boilerOn() {
		try {
			open(getBoilerGpioPin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOff()
	 */
	@Override
	public void boilerOff() {
		try {
			close(getBoilerGpioPin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpenAll()
	 */
	@Override
	public void toOpenAll() {
		for (IGpioPin gpioPin : getAllKonturs()) {
			try {
				swichState(gpioPin, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private IDs18bConfig getDs18bConfig(String id) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Sensor> criteriaQuery = builder.createQuery(Sensor.class);
		Root<Sensor> s = criteriaQuery.from(Sensor.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(Sensor_.sensorId), id));
		TypedQuery<Sensor> query = em.createQuery(criteriaQuery);
		Sensor sensor = query.getSingleResult();
		return sensor;
	}

	/**
	 * @return
	 */
	private IGpioPin getBoilerGpioPin() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(GpioPin_.type), GpioPinType.BOILER));
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		GpioPin gpioPin = query.getSingleResult();
		return gpioPin;
	}

	/**
	 * @return
	 */
	private List<? extends IGpioPin> getAllKonturs() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(GpioPin_.type), GpioPinType.KONTUR));
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		List<GpioPin> rv = query.getResultList();
		return rv;
	}

	private void open(IGpioPin pin) throws IOException {
		swichState(pin, true);
	}

	private void close(IGpioPin pin) throws IOException {
		swichState(pin, false);
	}

	private void swichState(IGpioPin pin, boolean state) throws IOException {
		if ((pin.isNormaliClosed() && state)
				|| (!pin.isNormaliClosed() && !state)) {
			if (!PinsUtil.isEnabledPin(pin)) {
				PinsUtil.setPinHigh(pin);
			}
		} else {
			if (PinsUtil.isEnabledPin(pin)) {
				PinsUtil.setPinLow(pin);
			}
		}
	}

}
