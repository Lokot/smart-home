package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;
import ru.skysoftlab.smarthome.heating.gpio.IGpioPin;

/**
 * Пин на плате.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class GpioPin implements IGpioPin, Serializable {

	private static final long serialVersionUID = -7517168275011878703L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/** Виртуальный пин. */
	private Integer gpio;
	/** Пин на материнской плате. */
	private Integer pin;
	/** Идентификационный номер на плате. */
	private String def;
	private String name;
	private Boolean normaliClosed;
	@Enumerated(EnumType.STRING)
	private GpioPinType type;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OWNER_ID")
	private Sensor owner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGpio() {
		return gpio;
	}

	public void setGpio(Integer gpio) {
		this.gpio = gpio;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		this.pin = pin;
	}

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return normaliClosed;
	}

	@Override
	public GpioPinType getType() {
		return this.type;
	}

	public void setType(GpioPinType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "GpioPin [name=" + name + ", type=" + type + "]";
	}

	public Sensor getOwner() {
		return owner;
	}

	public void setOwner(Sensor sensor) {
		this.owner = sensor;
        if (!sensor.getGpioPin().contains(this)) { 
        	// warning this may cause performance issues if you have a large data set since this operation is O(n)
        	sensor.getGpioPin().add(this);
        }
	}

}