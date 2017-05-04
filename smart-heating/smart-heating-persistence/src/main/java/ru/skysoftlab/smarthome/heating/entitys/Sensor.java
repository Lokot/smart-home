package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;

import ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig;

/**
 * Датчик.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class Sensor implements IDs18bConfig, Serializable, Cloneable {

	private static final long serialVersionUID = 1836369034208284654L;

	@Id
	private String sensorId;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Valve> gpioPin = new HashSet<>();
	private Float low;
	private Float top;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASTER_ID")
	private Boiler master;

	public Sensor() {

	}

	public Sensor(String sensorId, Float low, Float top) {
		this.sensorId = sensorId;
		this.low = low;
		this.top = top;
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

	@Override
	public Set<Valve> getGpioPin() {
		return gpioPin;
	}

	public void setGpioPin(Set<Valve> gpioPin) {
		this.gpioPin = gpioPin;
	}

	@Transient
	public void addGpioPin(Valve pin) {
		this.gpioPin.add(pin);
		if (pin.getOwner() != this) {
			pin.setOwner(this);
		}
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public void setTop(Float top) {
		this.top = top;
	}

	@Override
	@Transient
	public String getDeviceName() {
		return sensorId;
	}

	@Override
	public Float getTop() {
		return top;
	}

	@Override
	public Float getLow() {
		return low;
	}
	
	@Override
	public Boiler getMaster() {
		return master;
	}

	public void setMaster(Boiler master) {
		this.master = master;
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
		return name;
		// return "Sensor [id=" + id + ", sensorId=" + sensorId + ", name=" +
		// name
		// + ", gpioPin=" + gpioPin + ", low=" + low + ", top=" + top
		// + "]";
	}

	public String toLog() {
		return "Sensor [sensorId=" + sensorId + ", name=" + name + ", gpioPin=" + gpioPin + ", low="
				+ low + ", top=" + top + "]";
	}

}