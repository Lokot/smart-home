package ru.skysoftlab.smarthome.heating.dto;

public class TemperatureDto {
	
	private String sensorName;
	
	private Float temp;

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}
	
}
