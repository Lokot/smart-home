package ru.skysoftlab.smarthome.heating.entitys;

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

import ru.skysoftlab.skylibs.common.EditableEntity;
import ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig;

/**
 * Датчик.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class Sensor implements IDs18bConfig, EditableEntity<String>, Cloneable {

	private static final long serialVersionUID = 1836369034208284654L;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<Valve> gpioPin = new HashSet<>();
	private Float low;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MASTER_ID")
	private Boiler master;
	private String name;
	@Id
	private String sensorId;
	private Float top;

	public Sensor() {

	}

	public Sensor(String sensorId, Float low, Float top) {
		this.sensorId = sensorId;
		this.low = low;
		this.top = top;
	}

	@Transient
	public void addGpioPin(Valve pin) {
		this.gpioPin.add(pin);
		if (pin.getOwner() != this) {
			pin.setOwner(this);
		}
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
	@Transient
	public String getDeviceName() {
		return sensorId;
	}

	@Override
	public Set<Valve> getGpioPin() {
		return gpioPin;
	}

	@Override
	public String getId() {
		return sensorId;
	}

	@Override
	public Float getLow() {
		return low;
	}

	@Override
	public Boiler getMaster() {
		return master;
	}

	public String getName() {
		return name;
	}

	public String getSensorId() {
		return sensorId;
	}

	@Override
	public Float getTop() {
		return top;
	}

	public void setGpioPin(Set<Valve> gpioPin) {
		this.gpioPin = gpioPin;
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public void setMaster(Boiler master) {
		this.master = master;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public void setTop(Float top) {
		this.top = top;
	}

	public String toLog() {
		return "Sensor [sensorId=" + sensorId + ", name=" + name + ", gpioPin=" + gpioPin + ", low=" + low + ", top="
				+ top + "]";
	}

	@Override
	public String toString() {
		return name;
		// return "Sensor [id=" + id + ", sensorId=" + sensorId + ", name=" +
		// name
		// + ", gpioPin=" + gpioPin + ", low=" + low + ", top=" + top
		// + "]";
	}

}