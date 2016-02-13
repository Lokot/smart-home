package smarthome.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import smarthome.Sensors;

@Entity
public class Temp implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private float temp;
	private Sensors sensor;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public Temp() {
	}

	public Temp(Long id, float temp) {
		this.id = id;
		this.temp = temp;
	}

	public float getTemp() {
		return this.temp;
	}

	public void setTemp(float temp) {
		this.temp = temp;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Sensors getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensors sensor) {
		this.sensor = sensor;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

/*
 * Location: E:\classes\
 * 
 * Qualified Name: smarthome.entitys.Temp
 * 
 * JD-Core Version: 0.7.0.1
 */