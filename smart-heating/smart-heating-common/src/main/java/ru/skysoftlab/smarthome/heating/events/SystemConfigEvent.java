package ru.skysoftlab.smarthome.heating.events;

import java.util.Map;

/**
 * Назначение нового интервала сканирования.
 * 
 * @author Локтионов А.Г.
 *
 */
public class SystemConfigEvent {

	private final Map<String, Object> params;

	public SystemConfigEvent(Map<String, Object> params) {
		this.params = params;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public <T> T getParam(String key) {
		return (T) getParams().get(key);
	}

}
