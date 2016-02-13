package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GpioPin implements Serializable {

	private static final long serialVersionUID = -7517168275011878703L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private int gpio;
	private int pin;
	private String def;
	private String name;
	private boolean normaliClosed;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGpio() {
		return gpio;
	}

	public void setGpio(int gpio) {
		this.gpio = gpio;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
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

	public boolean isNormaliClosed() {
		return normaliClosed;
	}

	public void setNormaliClosed(boolean normaliClosed) {
		this.normaliClosed = normaliClosed;
	}
}