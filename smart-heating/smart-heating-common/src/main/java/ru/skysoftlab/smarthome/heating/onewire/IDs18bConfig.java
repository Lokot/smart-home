package ru.skysoftlab.smarthome.heating.onewire;

import java.util.Set;

import ru.skysoftlab.smarthome.heating.devices.IDevice;

/**
 * Класс настроек датчика.
 * 
 * @author Артём
 *
 */
public interface IDs18bConfig {

	/**
	 * Возвращает идентификатор датчика.
	 * 
	 * @return
	 */
	public String getDeviceName();

	/**
	 * Возвращает максимальную температуру.
	 * 
	 * @return
	 */
	public Float getTop();

	/**
	 * Возвращает минимальную температуру.
	 * 
	 * @return
	 */
	public Float getLow();

	/**
	 * Возвращает устройства, управляемые датчиком.
	 * 
	 * @return
	 */
	public Set<? extends IDevice> getGpioPin();

}
