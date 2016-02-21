package ru.skysoftlab.smarthome.heating.owfs;

/**
 * Обработчик событий изменения температуры.
 * 
 * @author Артём
 *
 */
public interface ITempAlarmHandler {

	/**
	 * Обрабатывает изменение температуры.
	 * 
	 * @param event
	 *            событие изменения температуры
	 */
	public void handleAlarm(TempAlarmingEvent event);
}
