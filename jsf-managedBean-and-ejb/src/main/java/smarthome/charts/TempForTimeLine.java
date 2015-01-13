package smarthome.charts;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import smarthome.HibernateUtil;
import smarthome.Sensors;
import smarthome.entitys.Temp;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@ManagedBean
@RequestScoped
public class TempForTimeLine implements Serializable {
	private String dataSource;
	private final String series;
	private String title;
	private Date date = new Date();

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static String argumentField = "time";

	public TempForTimeLine() {
		JsonArray rv = new JsonArray();
		for (Sensors sensor : Sensors.values()) {
			JsonObject obj = new JsonObject();
			obj.addProperty("valueField", String.valueOf(sensor.getIndex()));
			obj.addProperty("name", sensor.getName());
			rv.add(obj);
		}
		this.series = rv.toString();

		GregorianCalendar calendar = new GregorianCalendar(Locale.forLanguageTag("ru"));

		GregorianCalendar calendarStart = new GregorianCalendar(calendar.get(1), calendar.get(2), calendar.get(5), 0, 0);
		this.date = calendarStart.getTime();
	}

	public void createChart() {
		this.title = ("Температура за сутки " + new SimpleDateFormat("dd MMM YYYY").format(this.date));
		long start = this.date.getTime();
		long stop = start + 86400000L;

		JsonArray rv_dataSource = new JsonArray();
		EntityManager em = HibernateUtil.getEm();
		TypedQuery<Temp> query = em.createQuery("SELECT e FROM Temp e WHERE e.date>=? AND e.date<=?", Temp.class);
		query.setParameter(1, new Date(start));
		query.setParameter(2, new Date(stop));
		Date time = null;
		JsonObject temps = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		for (Temp temp : query.getResultList()) {
			String tempTime = dateFormat.format(temp.getDate());
			if (time == null) {
				time = temp.getDate();
				temps = new JsonObject();
				temps.addProperty(argumentField, tempTime);
				temps.addProperty(String.valueOf(temp.getSensor().getIndex()), Float.valueOf(temp.getTemp()));
			} else if (temp.getDate().getTime() < time.getTime() + 5000L) {
				temps.addProperty(String.valueOf(temp.getSensor().getIndex()), Float.valueOf(temp.getTemp()));
			} else {
				rv_dataSource.add(temps);
				time = temp.getDate();
				temps = new JsonObject();
				temps.addProperty(argumentField, tempTime);
				temps.addProperty(String.valueOf(temp.getSensor().getIndex()), Float.valueOf(temp.getTemp()));
			}
		}
		rv_dataSource.add(temps);
		this.dataSource = rv_dataSource.toString();
	}

	public String getTitle() {
		return this.title;
	}

	public String getDataSource() {
		return this.dataSource;
	}

	public String getSeries() {
		return this.series;
	}

	public String getArgumentField() {
		return argumentField;
	}
}