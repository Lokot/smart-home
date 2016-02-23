package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.beanutils.BeanUtils;

import ru.skysoftlab.smarthome.heating.owfs.IDs18bConfig;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String sensorId;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "owner")
	private Set<GpioPin> gpioPin = new HashSet<>();
	private Float low;
	private Float top;

	public Sensor() {

	}

	public Sensor(String sensorId, Float low, Float top) {
		this.sensorId = sensorId;
		this.low = low;
		this.top = top;
	}

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

	@Override
	public Set<GpioPin> getGpioPin() {
		return gpioPin;
	}

	public void setGpioPin(Set<GpioPin> gpioPin) {
		this.gpioPin = gpioPin;
	}

	@Transient
	public void addGpioPin(GpioPin pin) {
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
	public Sensor clone() throws CloneNotSupportedException {
		try {
			return (Sensor) BeanUtils.cloneBean(this);
		} catch (Exception ex) {
			throw new CloneNotSupportedException();
		}
	}

	@Override
	public String toString() {
		return "Sensor [id=" + id + ", sensorId=" + sensorId + ", name=" + name
				+ ", gpioPin=" + gpioPin + ", low=" + low + ", top=" + top
				+ "]";
	}

}