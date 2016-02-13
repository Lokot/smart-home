package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Датчик.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class Sensor implements Serializable {

	private static final long serialVersionUID = 1836369034208284654L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String sensorId;
	private String name;
	@OneToMany
	private Set<GpioPin> gpioPin;
	private float maxTemp;

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}
}