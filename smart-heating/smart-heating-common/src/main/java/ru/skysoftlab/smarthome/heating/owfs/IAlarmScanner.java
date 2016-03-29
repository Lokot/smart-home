package ru.skysoftlab.smarthome.heating.owfs;

import java.io.IOException;
import java.io.Serializable;

import org.owfs.jowfsclient.OwfsException;

/**
 * Интерфейс сканера температур.
 * 
 * @author Артём
 *
 */
public interface IAlarmScanner extends Serializable {

	/**
	 * Обновляет обработчик изменения температуры.
	 * 
	 * @param config
	 */
	public void setAlarmingDeviceHandler(IDs18bConfig config)
			throws IOException, OwfsException;

}
