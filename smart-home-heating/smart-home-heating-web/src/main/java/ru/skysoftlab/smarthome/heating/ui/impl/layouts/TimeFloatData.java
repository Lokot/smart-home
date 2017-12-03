package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

//import at.downdrown.vaadinaddons.highchartsapi.model.data.base.HighChartsBaseData;

public class TimeFloatData /* implements HighChartsBaseData */ {
	private long time;
	private float value;

	public TimeFloatData(long name, float value) {
		this.time = name;
		this.value = value;
	}

	// @Override
	// public String getHighChartValue() {
	// return "[" + time + ", " + value + "]";
	// }

	public long getTime() {
		return time;
	}

	public float getValue() {
		return value;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setValue(float value) {
		this.value = value;
	}

	// @Override
	// public String toString() {
	// return this.getHighChartValue();
	// }

}
