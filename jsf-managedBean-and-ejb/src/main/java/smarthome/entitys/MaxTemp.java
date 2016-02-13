package smarthome.entitys;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import smarthome.Sensors;

@Entity
public class MaxTemp implements Serializable {
	
	private static final long serialVersionUID = 3490330696232256823L;
	
	@Id
	@Enumerated(EnumType.STRING)
	private Sensors sensor;
	private float temp;

	public float getTemp() {
		return this.temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public Sensors getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensors sensor) {
		this.sensor = sensor;
	}
}