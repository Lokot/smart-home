package ru.skysoftlab.smarthome.heating.entitys;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import ru.skysoftlab.gpio.DigitalPin;
import ru.skysoftlab.skylibs.common.EditableEntity;
import ru.skysoftlab.skylibs.types.PinDbType;
import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.devices.IDevice;

@Entity
@TypeDef(name = "pinType", typeClass = PinDbType.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Device implements EditableEntity<DigitalPin>, IDevice {

	private static final long serialVersionUID = 5015244199697926557L;

	/** Идентификационный номер на плате. */
	@Id
	@Type(type = "pinType")
	private DigitalPin def;

	private String name;

	private Boolean normaliClosed = false;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Device other = (Device) obj;
		if (def == null) {
			if (other.def != null) {
				return false;
			}
		} else if (!def.equals(other.def)) {
			return false;
		}
		return true;
	}

	@Override
	public DigitalPin getDef() {
		return def;
	}

	@Override
	public DigitalPin getId() {
		return getDef();
	}

	@Override
	public String getName() {
		return name;
	}

	public Boolean getNormaliClosed() {
		return normaliClosed;
	}

	@Override
	@Transient
	public abstract DeviceType getType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((def == null) ? 0 : def.hashCode());
		return result;
	}

	@Override
	@Transient
	public Boolean isNormaliClosed() {
		return getNormaliClosed();
	}

	public void setDef(DigitalPin def) {
		this.def = def;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNormaliClosed(Boolean normaliClosed) {
		this.normaliClosed = normaliClosed;
	}

	@Override
	public String toString() {
		return getName();
	}

}
