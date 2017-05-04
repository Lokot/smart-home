package ru.skysoftlab.smarthome.heating.devices;

import java.io.Serializable;
import java.util.Collection;

/**
 * Интерфейс контроллера устройств.
 * 
 * @author Артём
 *
 */
public interface IDevicesController extends Serializable {

	/**
	 * Открывает контур отопления.
	 * 
	 * @param deviceName
	 */
	public void openHC(String deviceName);

	/**
	 * Закрывает контур отопления.
	 * 
	 * @param deviceName
	 */
	public void closeHC(String deviceName);

	/**
	 * Возвращает массив имен пинов на контроллере.
	 * 
	 * @return
	 */
	public Collection<String> getPinNames();

}
