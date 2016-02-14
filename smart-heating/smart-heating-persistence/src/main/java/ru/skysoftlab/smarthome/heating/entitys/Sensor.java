package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Датчик.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class Sensor implements Serializable, Cloneable {

	private static final long serialVersionUID = 1836369034208284654L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String sensorId;
	private String name;
	@OneToMany
	private Set<GpioPin> gpioPin;
	private Float maxTemp;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<GpioPin> getGpioPin() {
		return gpioPin;
	}

	public void setGpioPin(Set<GpioPin> gpioPin) {
		this.gpioPin = gpioPin;
	}

	public Float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Float maxTemp) {
		this.maxTemp = maxTemp;
	}

	@Override
	public Sensor clone() throws CloneNotSupportedException {
		try {
			return (Sensor) BeanUtils.cloneBean(this);
		} catch (Exception ex) {
			throw new CloneNotSupportedException();
		}
	}

	@Override
	public String toString() {
		return "Sensor [sensorId=" + sensorId + ", name=" + name + ", maxTemp="
				+ maxTemp + "]";
	}

}