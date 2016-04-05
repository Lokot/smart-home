package ru.skysoftlab.smarthome.heating.owfs;

import java.io.Serializable;

/**
 * Интерфейс сканера температур.
 * 
 * @author Loktionov Artem
 *
 */
public interface IAlarmScanner extends Serializable {

	/**
	 * Обновляет обработчик изменения температуры.
	 * 
	 * @param config
	 */
	public void setAlarmingDeviceHandler(IDs18bConfig config);
	
	/**
	 * Возвращает есть ли обработчик.
	 * 
	 * @param deviceName
	 * @return
	 */
	public boolean isAlarmingDeviceHandlerInstalled(String deviceName);

}
