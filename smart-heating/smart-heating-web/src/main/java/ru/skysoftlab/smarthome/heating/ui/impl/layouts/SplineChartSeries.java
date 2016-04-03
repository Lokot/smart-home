package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.util.ArrayList;
import java.util.List;

import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.HighChartsData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.DoubleData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.DoubleDoubleData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.DoubleIntData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.FloatData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.IntData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.IntDoubleData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.IntIntData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.StringDoubleData;
import at.downdrown.vaadinaddons.highchartsapi.model.data.base.StringIntData;
import at.downdrown.vaadinaddons.highchartsapi.model.series.HighChartsSeries;

public class SplineChartSeries implements HighChartsSeries {

	private String sName;

	private List<HighChartsData> data = new ArrayList<>();
	
	public SplineChartSeries(String sName) {
		this.sName = sName;
	}
	
	public SplineChartSeries(String sName, List<HighChartsData> data) {
		this.sName = sName;
		this.data = data;
	}

	@Override
	public String getHighChartValue() {
		StringBuilder builder = new StringBuilder();
        builder.append("{ name: '" + this.sName + "', data: [");

        int count = 1;
        if (data != null) {

            for (HighChartsData data : data) {
                if (count == 1) {
                    builder.append(data.getHighChartValue());
                } else if (count > 1) {
                    builder.append(",");
                    builder.append(data.getHighChartValue());
                }
                count++;
            }
        }

        builder.append("]");
        builder.append("}");

        return builder.toString();
	}

	@Override
	public ChartType getChartType() {
		return ChartType.SPLINE;
	}

	/**
	 * Add an {@link HighChartsData} object to your series.
	 *
	 * @param highChartsData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(HighChartsData highChartsData) {
		this.data.add(highChartsData);
	}

	/**
	 * Add an {@link Integer} object to your series.
	 *
	 * @param value
	 *            - The object you want to add.
	 */
	@Override
	public void addData(int value) {
		this.data.add(new IntData(value));
	}

	/**
	 * Add an {@link Float} object to your series.
	 *
	 * @param value
	 *            - The object you want to add.
	 */
	@Override
	public void addData(float value) {
		this.data.add(new FloatData(value));
	}

	/**
	 * Add an {@link Double} object to your series.
	 *
	 * @param value
	 *            - The object you want to add.
	 */
	@Override
	public void addData(double value) {
		this.data.add(new DoubleData(value));
	}

	/**
	 * Add an {@link DoubleDoubleData} object to your series.
	 *
	 * @param doubleDoubleData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(DoubleDoubleData doubleDoubleData) {
		this.data.add(doubleDoubleData);
	}

	/**
	 * Add an {@link DoubleIntData} object to your series.
	 *
	 * @param doubleIntData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(DoubleIntData doubleIntData) {
		this.data.add(doubleIntData);
	}

	/**
	 * Add an {@link IntDoubleData} object to your series.
	 *
	 * @param intDoubleData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(IntDoubleData intDoubleData) {
		this.data.add(intDoubleData);
	}

	/**
	 * Add an {@link IntIntData} object to your series.
	 *
	 * @param intIntData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(IntIntData intIntData) {
		this.data.add(intIntData);
	}

	/**
	 * Add an {@link StringDoubleData} object to your series
	 *
	 * @param stringDoubleData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(StringDoubleData stringDoubleData) {
		this.data.add(stringDoubleData);
	}

	/**
	 * Add an {@link StringIntData} object to your series
	 *
	 * @param stringIntData
	 *            - The object you want to add.
	 */
	@Override
	public void addData(StringIntData stringIntData) {
		this.data.add(stringIntData);
	}
	
	public void addData(TimeFloatData stringIntData) {
		this.data.add(stringIntData);
	}
	
	/**
	 * Clears the list of data.
	 */
	@Override
	public void clearData() {
		this.data.clear();
	}

	@Override
	public String toString() {
		return this.getHighChartValue();
	}

}
