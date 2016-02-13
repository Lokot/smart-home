package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Показания датчика.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
public class Temp implements Serializable {

	private static final long serialVersionUID = -928474898602043274L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private float temp;
	@OneToOne
	private Sensor sensor;
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

	public Sensor getSensor() {
		return this.sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}