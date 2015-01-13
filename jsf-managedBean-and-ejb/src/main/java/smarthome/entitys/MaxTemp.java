package smarthome.entitys;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import smarthome.Sensors;

@Entity
public class MaxTemp implements Serializable {
	@Id
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

/*
 * Location: E:\classes\
 * 
 * Qualified Name: smarthome.entitys.MaxTemp
 * 
 * JD-Core Version: 0.7.0.1
 */