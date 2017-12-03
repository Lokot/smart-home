package ru.skysoftlab.smarthome.heating.devices;

import java.io.Serializable;
import java.util.Collection;

import ru.skysoftlab.gpio.DigitalPin;

/**
 * Интерфейс контроллера устройств.
 * 
 * @author Артём
 *
 */
public interface IDevicesController extends Serializable {

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
	public Collection<DigitalPin> getPinNames();

	/**
	 * Открывает контур отопления.
	 * 
	 * @param deviceName
	 */
	public void openHC(String deviceName);

}
