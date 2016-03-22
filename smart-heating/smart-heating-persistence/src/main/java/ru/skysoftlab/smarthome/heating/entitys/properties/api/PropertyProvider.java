package ru.skysoftlab.smarthome.heating.entitys.properties.api;

import java.util.Date;

public interface PropertyProvider {

	public String getStringValue(String key);

	public Integer getIntegerValue(String key);

	public Long getLongValue(String key);
	
	public Double getDoubleValue(String key);
	
	public Boolean getBooleanValue(String key);

	public Date getDateValue(String key);
	
	public void setStringValue(String key, String value, String name);

	public void setIntegerValue(String key, Integer value, String name);

	public void setLongValue(String key, Long value, String name);
	
	public void setDoubleValue(String key, Double value, String name);
	
	public void setBooleanValue(String key, Boolean value, String name);

	public void setDateValue(String key, Date value, String name);
	
}
