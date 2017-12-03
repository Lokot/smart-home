package ru.skysoftlab.smarthome.heating.entitys;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.devices.IValve;
import ru.skysoftlab.smarthome.heating.devices.ValveState;

/**
 * Кран (контур).
 * 
 * @author Lokot
 *
 */
@Entity
@DiscriminatorValue(value = "KONTUR")
public class Valve extends Device implements IValve {

	private static final long serialVersionUID = 3751248852660841124L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private Sensor owner;

	@Enumerated(EnumType.STRING)
	private ValveState valveState = ValveState.AUTO;

	public Sensor getOwner() {
		return owner;
	}

	@Override
	@Transient
	public DeviceType getType() {
		return DeviceType.KONTUR;
	}

	public ValveState getValveState() {
		return valveState;
	}

	public void setOwner(Sensor sensor) {
		this.owner = sensor;
		if (!sensor.getGpioPin().contains(this)) {
			// warning this may cause performance issues if you have a large
			// data set since this operation is O(n)
			sensor.getGpioPin().add(this);
		}
	}

	public void setValveState(ValveState valveState) {
		this.valveState = valveState;
	}

}
