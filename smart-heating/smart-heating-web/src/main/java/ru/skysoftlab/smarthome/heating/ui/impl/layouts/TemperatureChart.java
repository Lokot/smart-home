package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.util.ArrayList;
import java.util.List;

import ru.skysoftlab.smarthome.heating.dto.FloatChartData;
import ru.skysoftlab.smarthome.heating.ui.IReloadedComponent;
import at.downdrown.vaadinaddons.highchartsapi.Colors;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.HighChartFactory;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.data.HighChartsData;
import at.downdrown.vaadinaddons.highchartsapi.model.series.LineChartSeries;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

/**
 * График температур.
 * 
 * @author Артём
 *
 */
public class TemperatureChart extends VerticalLayout implements
		IReloadedComponent {

	private static final long serialVersionUID = 1307704684754077226L;

	@Override
	public void reload() {
		// *** LINE ***
		ChartConfiguration lineConfiguration = new ChartConfiguration();
		lineConfiguration.setTitle("Температура за сутки");
		lineConfiguration.setChartType(ChartType.LINE);
		lineConfiguration.setBackgroundColor(Colors.WHITE);

		List<HighChartsData> bananaValues = new ArrayList<HighChartsData>();
		bananaValues.add(new FloatChartData(11.3f));
		bananaValues.add(new FloatChartData(25.1f));
		bananaValues.add(new FloatChartData(32.7f));

		LineChartSeries bananaLine = new LineChartSeries("Bananas",
				bananaValues);

		List<HighChartsData> sweetValues = new ArrayList<HighChartsData>();
		sweetValues.add(new FloatChartData(33.65f));
		sweetValues.add(new FloatChartData(63.24f));
		sweetValues.add(new FloatChartData(21.52f));

		LineChartSeries choclateLine = new LineChartSeries("Choclate",
				sweetValues);

		lineConfiguration.getSeriesList().add(bananaLine);
		lineConfiguration.getSeriesList().add(choclateLine);

		try {
			HighChart lineChart = HighChartFactory.renderChart(lineConfiguration);
			lineChart.setHeight(80, Unit.PERCENTAGE);
			lineChart.setWidth(80, Unit.PERCENTAGE);
			System.out.println("LineChart Script : "
					+ lineConfiguration.getHighChartValue());
			addComponent(lineChart);
			setComponentAlignment(lineChart, Alignment.MIDDLE_CENTER);
		} catch (HighChartsException e) {
			e.printStackTrace();
		}
	}

}
