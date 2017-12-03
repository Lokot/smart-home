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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import ru.skysoftlab.skylibs.joda.types.LocalDateUserType;
import ru.skysoftlab.skylibs.joda.types.LocalTimeUserType;

/**
 * Показания датчика.
 * 
 * @author Loktionov Artem
 *
 */
@Entity
@TypeDefs({ @TypeDef(name = "dateType", typeClass = LocalDateUserType.class),
		@TypeDef(name = "timeType", typeClass = LocalTimeUserType.class) })
@NamedQueries({ @NamedQuery(name = "Temp.byDate", query = "SELECT e FROM Temp e WHERE e.date=:date") })
public class Temp implements Serializable {

	private static final long serialVersionUID = -928474898602043274L;

	@Type(type = "dateType")
	private LocalDate date;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	private Sensor sensor;
	private Float temp;
	@Type(type = "timeType")
	private LocalTime time;

	public Temp() {

	}

	public Temp(Float temp, Sensor sensor, Date date) {
		this.temp = temp;
		this.sensor = sensor;
		LocalDateTime t = LocalDateTime.fromDateFields(date);
		this.time = t.toLocalTime();
		this.date = t.toLocalDate();
	}

	public LocalDate getDate() {
		return date;
	}

	public Long getId() {
		return this.id;
	}

	public Sensor getSensor() {
		return this.sensor;
	}

	public Float getTemp() {
		return this.temp;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Temp setSensor(Sensor sensor) {
		this.sensor = sensor;
		return this;
	}

	public Temp setTemp(Float temp) {
		this.temp = temp;
		return this;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

}