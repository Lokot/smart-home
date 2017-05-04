package ru.skysoftlab.smarthome.heating.onewire;

import java.io.Serializable;

/**
 * Интерфейс сканера температур.
 * 
 * @author Loktionov Artem
 *
 */
public interface IAlarmScannerJob extends Serializable {

	/**
	 * Обновляет обработчик изменения температуры.
	 * 
	 * @param config
	 */
	public void setDeviceConfig(IDs18bConfig config);
	
	/**
	 * Возвращает есть ли обработчик.
	 * 
	 * @param deviceName
	 * @return
	 */
//	public boolean isAlarmingDeviceHandlerInstalled(String deviceName);

}
