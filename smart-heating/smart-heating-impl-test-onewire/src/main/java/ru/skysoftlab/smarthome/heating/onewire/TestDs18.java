package ru.skysoftlab.smarthome.heating.onewire;

public class TestDs18 {

	private String id;
	private Float temp;
	private Float lowTemp;
	private Float maxTemp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public Float getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(Float lowTemp) {
		this.lowTemp = lowTemp;
	}

	public Float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public boolean isAlarmed() {
		if(temp>=maxTemp || temp<=lowTemp) return true;
		return false;
	}
	
}
