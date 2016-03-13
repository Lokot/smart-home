package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import javax.annotation.PostConstruct;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.DashBoardElementQualifier;
import ru.skysoftlab.smarthome.heating.ui.IDashboardModule;
import at.downdrown.vaadinaddons.highchartsapi.Colors;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;
import at.downdrown.vaadinaddons.highchartsapi.model.series.LineChartSeries;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

/**
 * График температур.
 * 
 * @author Артём
 *
 */
@UIScoped
@DashBoardElementQualifier(view = NavigationService.STATISTIC, name = "chart", order=1)
public class TemperatureChart extends VerticalLayout implements
		IDashboardModule {

	private static final long serialVersionUID = 1307704684754077226L;

	private HighChart lineChart;

	@PostConstruct
	private void init() {
		try {
			ChartConfiguration lineConfiguration = getConfig();
			lineChart = new HighChart();
			lineChart.setChartoptions(lineConfiguration.getHighChartValue());
			lineChart.setChartConfiguration(lineConfiguration);
			lineChart.setHeight(80, Unit.PERCENTAGE);
			lineChart.setWidth(80, Unit.PERCENTAGE);
			addComponent(lineChart);
			setComponentAlignment(lineChart, Alignment.MIDDLE_CENTER);
		} catch (HighChartsException e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

	@Override
	public void reload() {
		try {
			lineChart.redraw(getConfig());
		} catch (HighChartsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}

	}

	private ChartConfiguration getConfig() {
		// *** LINE ***
		ChartConfiguration lineConfiguration = new ChartConfiguration();
		lineConfiguration.setTitle("Температура за сутки");
		lineConfiguration.setChartType(ChartType.LINE);
		lineConfiguration.setBackgroundColor(Colors.WHITE);

		LineChartSeries bananaLine = new LineChartSeries("Bananas");
		bananaLine.addData(11.3f);
		bananaLine.addData(25.1f);
		bananaLine.addData(32.7f);

		LineChartSeries choclateLine = new LineChartSeries("Choclate");
		choclateLine.addData(33.65f);
		choclateLine.addData(63.24f);
		choclateLine.addData(21.52f);

		lineConfiguration.getSeriesList().add(bananaLine);
		lineConfiguration.getSeriesList().add(choclateLine);
		return lineConfiguration;
	}

}
