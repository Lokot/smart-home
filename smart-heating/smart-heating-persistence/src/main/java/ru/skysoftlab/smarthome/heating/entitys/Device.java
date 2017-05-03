package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.devices.IDevice;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "type" )
public abstract class Device implements Serializable, IDevice {
	
	private static final long serialVersionUID = 5015244199697926557L;

	/** Идентификационный номер на плате. */
	@Id
	private String def;
	
	private String userName;
	
	private Boolean normaliClosed = false;

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public Boolean getNormaliClosed() {
		return normaliClosed;
	}

	public void setNormaliClosed(Boolean normaliClosed) {
		this.normaliClosed = normaliClosed;
	}

	@Override
	@Transient
	public Boolean isNormaliClosed() {
		return getNormaliClosed();
	}
	
	@Transient
	public abstract DeviceType getType();
}
