package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MaxTemp implements Serializable {
	
	private static final long serialVersionUID = 3490330696232256823L;
	
	@Id
	private Sensor sensor;
	private float temp;

	public float getTemp() {
		return this.temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public Sensor getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}