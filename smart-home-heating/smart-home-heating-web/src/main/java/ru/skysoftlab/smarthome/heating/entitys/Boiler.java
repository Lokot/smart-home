package ru.skysoftlab.smarthome.heating.entitys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.devices.IBoiler;

/**
 * Нагреватель.
 * 
 * @author Lokot
 *
 */
@Entity
@DiscriminatorValue(value = "BOILER")
public class Boiler extends Device implements IBoiler {

	private static final long serialVersionUID = 8690957614498526729L;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "master")
	private Set<Sensor> sensors = new HashSet<>();

	@Transient
	public void addGpioPin(Sensor sensor) {
		this.sensors.add(sensor);
		if (sensor.getMaster() != this) {
			sensor.setMaster(this);
		}
	}

	public Set<Sensor> getSensors() {
		return sensors;
	}

	@Override
	@Transient
	public DeviceType getType() {
		return DeviceType.BOILER;
	}

	public void setSensors(Set<Sensor> sensors) {
		this.sensors = sensors;
	}

}
