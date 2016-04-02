package ru.skysoftlab.smarthome.heating.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({ @NamedQuery(name = "Temp.byDate", query = "SELECT e FROM Temp e WHERE e.date>=:start AND e.date<:stop") })
public class Temp implements Serializable {

	private static final long serialVersionUID = -928474898602043274L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Float temp;
	@OneToOne
	private Sensor sensor;
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	// TODO время и дата отдельно

	public Temp() {

	}

	public Temp(Float temp, Sensor sensor, Date date) {
		this.temp = temp;
		this.sensor = sensor;
		this.date = date;
	}

	public Float getTemp() {
		return this.temp;
	}

	public Temp setTemp(Float temp) {
		this.temp = temp;
		return this;
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

	public Temp setSensor(Sensor sensor) {
		this.sensor = sensor;
		return this;
	}

	public Date getDate() {
		return this.date;
	}

	public Temp setDate(Date date) {
		this.date = date;
		return this;
	}
}