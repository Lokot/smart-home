package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

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
import at.downdrown.vaadinaddons.highchartsapi.model.series.LineChartSeries;

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
	@Inject
	private EntityManager em;
	// Create a DateField with the default style
	DateField date = new DateField();

	private HighChart lineChart;

	public TemperatureChart() {
		// Set the date and time to present
		date.setValue(new Date());
		date.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = -5911805285102919911L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				reload();
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
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
	}

	@Override
	public void reload() {
		date.setValue(new Date());
		try {
			lineChart.redraw(getConfig());
		} catch (HighChartsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}

	}

	private ChartConfiguration getConfig() {
		List<String> timeCategories = new ArrayList<>();
		Map<LocalTime, List<Temp>> tempByDates = getTempByDates();
		for (LocalTime date : tempByDates.keySet()) {
			timeCategories.add(date.toString("HH:mm"));	
		}
		Axis xAxis = new Axis(AxisType.xAxis);
		xAxis.setTitle("Время");
		xAxis.setAxisValueType(AxisValueType.DATETIME);
		xAxis.setCategories(timeCategories);
		Axis yAxis = new Axis(AxisType.yAxis);
		yAxis.setTitle("Температура (°C)");
		// TODO переделать график на плавный
		// *** LINE ***
		ChartConfiguration lineConfiguration = new ChartConfiguration();
		lineConfiguration.setTitle("Температура за сутки "
				+ new SimpleDateFormat("dd MMM YYYY").format(date.getValue()));
		lineConfiguration.setChartType(ChartType.LINE);
		lineConfiguration.setBackgroundColor(Colors.WHITE);
		lineConfiguration.setxAxis(xAxis);
		lineConfiguration.setyAxis(yAxis);

		for(LineChartSeries t: createSeries(tempByDates).values()){
			lineConfiguration.getSeriesList().add(t);
		}
		return lineConfiguration;
	}

	private Map<Sensor, LineChartSeries> createSeries(Map<LocalTime, List<Temp>> dateTemp) {
		Map<Sensor, LineChartSeries> rv = new HashMap<>();
		for (List<Temp> tempList : dateTemp.values()) {
			for (Temp temp : tempList) {
				Sensor sensor = temp.getSensor();
				LineChartSeries sensorSer = rv.get(sensor);
				if (sensorSer == null) {
					sensorSer = new LineChartSeries(sensor.getName());
					rv.put(sensor, sensorSer);
				}
				// TODO округлить до 1 знака после запятой
				sensorSer.addData(temp.getTemp());
			}
		}
		return rv;
	}

	private List<Temp> getDateTemp(Date date) {
		LocalDateTime ldt = LocalDateTime.fromDateFields(date).withTime(0, 0, 0, 0);
		// TODO Добавить временную зону
		Date start = ldt.toDateTime().toDate();
		Date stop = ldt.plusDays(1).toDateTime().toDate();
		TypedQuery<Temp> query = em.createNamedQuery("Temp.byDate", Temp.class);
		query.setParameter("start", start);
		query.setParameter("stop", stop);
		List<Temp> results = query.getResultList();
		return results;
	}

	private Map<LocalTime, List<Temp>> getTempByDates() {
		Map<LocalTime, List<Temp>> rv = new TreeMap<>(new Comparator<LocalTime>() {

			@Override
			public int compare(LocalTime o1, LocalTime o2) {
				return o1.compareTo(o2);
			}
		});
		List<Temp> tempList = getDateTemp(date.getValue());
		for (Temp temp : tempList) {
			LocalTime time = LocalTime.fromDateFields(temp.getDate());
			List<Temp> dateTempList = rv.get(time);
			if (dateTempList == null) {
				dateTempList = new ArrayList<>();
				rv.put(time, dateTempList);
			}
			dateTempList.add(temp);
		}
		return rv;
	}

}
