package ru.skysoftlab.smarthome.heating.dto;

import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_MAX_TEMP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_START;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_STOP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.INTERVAL;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.OWFS_SERVER;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.SUMMER_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.TEMP_INTERVAL;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ru.skysoftlab.skylibs.annatations.AppProperty;

public class SystemConfigDto implements Serializable {

	private static final long serialVersionUID = -1809506535079134130L;

	@Inject
	@AppProperty(INTERVAL)
	private String alarmInterval;

	@Inject
	@AppProperty(HOLIDAY_MODE)
	private Boolean holiday;

	@Inject
	@AppProperty(HOLIDAY_MODE_MAX_TEMP)
	private Float holidayMaxTemp;

	@Inject
	@AppProperty(HOLIDAY_MODE_START)
	private Date holidayStart;

	@Inject
	@AppProperty(HOLIDAY_MODE_STOP)
	private Date holidayStop;

	@Inject
	@AppProperty(SUMMER_MODE)
	private Boolean summerMode;

	@Inject
	@AppProperty(TEMP_INTERVAL)
	private String tempInterval;

	@Inject
	@AppProperty(OWFS_SERVER)
	private String url;

	public String getAlarmInterval() {
		return alarmInterval;
	}

	public Map<String, Object> getDataForEvent() {
		Map<String, Object> rv = new HashMap<>();
		rv.put(OWFS_SERVER, getUrl());
		rv.put(INTERVAL, getAlarmInterval());
		rv.put(TEMP_INTERVAL, getTempInterval());
		rv.put(SUMMER_MODE, getSummerMode());
		rv.put(HOLIDAY_MODE, getHoliday());
		rv.put(HOLIDAY_MODE_START, getHolidayStart());
		rv.put(HOLIDAY_MODE_STOP, getHolidayStop());
		rv.put(HOLIDAY_MODE_MAX_TEMP, getHolidayMaxTemp());
		return rv;
	}

	public Boolean getHoliday() {
		return holiday;
	}

	public Float getHolidayMaxTemp() {
		return holidayMaxTemp;
	}

	public Date getHolidayStart() {
		return holidayStart;
	}

	public Date getHolidayStop() {
		return holidayStop;
	}

	public Boolean getSummerMode() {
		return summerMode;
	}

	public String getTempInterval() {
		return tempInterval;
	}

	public String getUrl() {
		return url;
	}

	public void setAlarmInterval(String alarmInterval) {
		this.alarmInterval = alarmInterval;
	}

	public void setHoliday(Boolean holiday) {
		this.holiday = holiday;
	}

	public void setHolidayMaxTemp(Float holidayMaxTemp) {
		this.holidayMaxTemp = holidayMaxTemp;
	}

	public void setHolidayStart(Date holidayStart) {
		this.holidayStart = holidayStart;
	}

	public void setHolidayStop(Date holidayStop) {
		this.holidayStop = holidayStop;
	}

	public void setSummerMode(Boolean summerMode) {
		this.summerMode = summerMode;
	}

	public void setTempInterval(String tempInterval) {
		this.tempInterval = tempInterval;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
