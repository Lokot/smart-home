package ru.skysoftlab.smarthome.heating.entitys.properties.api;

import java.util.Date;

public interface PropertyProvider {

	public String getStringValue(String key);

	public Integer getIntegerValue(String key);

	public Long getLongValue(String key);
	
	public Double getDoubleValue(String key);
	
	public Boolean getBooleanValue(String key);

	public Date getDateValue(String key);
	
}
