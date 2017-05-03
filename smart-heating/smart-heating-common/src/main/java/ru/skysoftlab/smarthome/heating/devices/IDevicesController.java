package ru.skysoftlab.smarthome.heating.devices;

import java.io.Serializable;

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
	 * Включает котел.
	 */
	public void boilerOn();

	/**
	 * Выключает котел.
	 */
	public void boilerOff();

	/**
	 * Открывает все контура отопления.
	 */
	public void openHCAll();

	/**
	 * Возвращает массив имен пинов на контроллере.
	 * 
	 * @return
	 */
	public String[] getPinNames();

}
