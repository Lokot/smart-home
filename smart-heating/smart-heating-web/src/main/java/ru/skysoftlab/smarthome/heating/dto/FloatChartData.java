package ru.skysoftlab.smarthome.heating.dto;

import at.downdrown.vaadinaddons.highchartsapi.model.data.HighChartsData;

/**
 * Данные для графика.
 * 
 * @author Артём
 *
 */
public class FloatChartData implements HighChartsData {

	private Float value;

	public FloatChartData(Float value) {
		this.value = value;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	@Override
	public String getHighChartValue() {
		return this.value.toString();
	}

	@Override
	public String toString() {
		return this.getHighChartValue();
	}

}
