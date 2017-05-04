package ru.skysoftlab.smarthome.heating.onewire;

import java.io.Serializable;

/**
 * Обработчик событий изменения температуры.
 * 
 * @author Артём
 *
 */
public interface ITempAlarmHandler extends Serializable {

	/**
	 * Обрабатывает изменение температуры.
	 * 
	 * @param event
	 *            событие изменения температуры
	 */
	public void handleAlarm(TempAlarmingEvent event);
	
}
