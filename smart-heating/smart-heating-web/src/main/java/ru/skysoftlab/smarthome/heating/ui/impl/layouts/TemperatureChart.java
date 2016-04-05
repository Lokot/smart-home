package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.DashBoardElementQualifier;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Temp;
import ru.skysoftlab.smarthome.heating.ui.IDashboardModule;
import at.downdrown.vaadinaddons.highchartsapi.Colors;
import at.downdrown.vaadinaddons.highchartsapi.HighChart;
import at.downdrown.vaadinaddons.highchartsapi.exceptions.HighChartsException;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis.AxisType;
import at.downdrown.vaadinaddons.highchartsapi.model.Axis.AxisValueType;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartConfiguration;
import at.downdrown.vaadinaddons.highchartsapi.model.ChartType;

import com.vaadin.cdi.UIScoped;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.DateField;
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
@DashBoardElementQualifier(view = NavigationService.STATISTIC, name = "chart", order = 1)
public class TemperatureChart extends VerticalLayout implements IDashboardModule {

	private static final long serialVersionUID = 1307704684754077226L;

	private Logger LOG = LoggerFactory.getLogger(TemperatureChart.class);

	@Inject
	private EntityManager em;

	private DateField date = new DateField();
	private HighChart lineChart;

	public TemperatureChart() {
		date.setValue(new Date());
		date.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5911805285102919911L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				redraw();
			}
		});
	}

	@PostConstruct
	private void init() {
		try {
			ChartConfiguration lineConfiguration = getConfig();
			lineChart = new HighChart();
			lineChart.setChartoptions(lineConfiguration.getHighChartValue());
			lineChart.setChartConfiguration(lineConfiguration);
			lineChart.setHeight(80, Unit.PERCENTAGE);
			lineChart.setWidth(80, Unit.PERCENTAGE);
			addComponent(date);
			addComponent(lineChart);
			setComponentAlignment(date, Alignment.MIDDLE_CENTER);
			setComponentAlignment(lineChart, Alignment.MIDDLE_CENTER);
		} catch (HighChartsException e) {
			LOG.error("Ошибка сериализации сонфигурации.", e);
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

	@Override
	public void reload() {
		date.setValue(new Date());
		redraw();
	}

	private void redraw() {
		try {
			lineChart.redraw(getConfig());
		} catch (HighChartsException e) {
			LOG.error("Ошибка отображения графика.", e);
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

	private ChartConfiguration getConfig() {
		Axis xAxis = new Axis(AxisType.xAxis);
		xAxis.setTitle("Время");
		xAxis.setAxisValueType(AxisValueType.DATETIME);
		Axis yAxis = new Axis(AxisType.yAxis);
		yAxis.setTitle("Температура (°C)");
		ChartConfiguration lineConfiguration = new ChartConfiguration();
		lineConfiguration.setTitle("Температура за сутки");
		lineConfiguration.setSubTitle(new SimpleDateFormat("dd MMM YYYY").format(date.getValue()));
		lineConfiguration.setChartType(ChartType.SPLINE);
		lineConfiguration.setBackgroundColor(Colors.WHITE);
		lineConfiguration.setxAxis(xAxis);
		lineConfiguration.setyAxis(yAxis);
		lineConfiguration.getSeriesList().addAll(getSeries());
		return lineConfiguration;
	}

	private Collection<SplineChartSeries> getSeries() {
		List<Temp> temps = getDateTemp(date.getValue());
		Map<Sensor, SplineChartSeries> rv = new HashMap<>();
		for (Temp temp : temps) {
			Sensor sensor = temp.getSensor();
			SplineChartSeries sensorSer = rv.get(sensor);
			if (sensorSer == null) {
				sensorSer = new SplineChartSeries(sensor.getName());
				rv.put(sensor, sensorSer);
			}
			sensorSer.addData(new TimeFloatData(temp.getTime().toDateTimeToday().getMillis(), temp.getTemp()));
		}
		return rv.values();
	}

	private List<Temp> getDateTemp(Date date) {
		// TODO Добавить временную зону
		TypedQuery<Temp> query = em.createNamedQuery("Temp.byDate", Temp.class);
		query.setParameter("date", LocalDate.fromDateFields(date));
		List<Temp> results = query.getResultList();
		return results;
	}

}
