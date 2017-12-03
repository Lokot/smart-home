package ru.skysoftlab.smarthome.dto;

import static ru.skysoftlab.smarthome.impl.ConfigurationNames.SIMPLE_PROP;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ru.skysoftlab.skylibs.annatations.AppProperty;

public class SystemConfigDto implements Serializable {

	private static final long serialVersionUID = -1809506535079134130L;

	@Inject
	@AppProperty(SIMPLE_PROP)
	private String simpleProp;

	public Map<String, Object> getDataForEvent() {
		Map<String, Object> rv = new HashMap<>();
		rv.put(SIMPLE_PROP, getSimpleProp());
		return rv;
	}

	public String getSimpleProp() {
		return simpleProp;
	}

	public void setSimpleProp(String simpleProp) {
		this.simpleProp = simpleProp;
	}

}
