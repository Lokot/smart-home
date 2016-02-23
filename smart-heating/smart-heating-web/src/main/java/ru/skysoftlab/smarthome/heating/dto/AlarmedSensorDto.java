package ru.skysoftlab.smarthome.heating.dto;

import java.io.Serializable;

public class AlarmedSensorDto implements Serializable {

	private static final long serialVersionUID = 710507709719534889L;
	
	private String name;

	private String sensorId;

	private Float low;

	private Float top;
	
	private Float fastTemp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public Float getLow() {
		return low;
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public Float getTop() {
		return top;
	}

	public void setTop(Float top) {
		this.top = top;
	}

	public Float getFastTemp() {
		return fastTemp;
	}

	public void setFastTemp(Float fastTemp) {
		this.fastTemp = fastTemp;
	}

}
