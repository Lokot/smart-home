package ru.skysoftlab.smarthome.heating.entitys.properties.api;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

@Local
public interface PropertyManager {
	
	public void setStringProperty(String key, String value);
	
	public void setIntegerProperty(String key, Integer value);

	public void setLongProperty(String key, Long value);
	
	public void setDoubleProperty(String key, Double value);
	
	public void setBooleanProperty(String key, Boolean value);

	public void setDateProperty(String key, Date value);
	
	public int customThumbnail(String thumbnailSequence);
	
	public List<ApplicationProperty<Integer>> customsThumbnail(String key);

}
