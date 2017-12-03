package ru.skysoftlab.smarthome.heating.devices;

import ru.skysoftlab.gpio.DigitalPin;

/**
 * Пин на материнской плате.
 * 
 * @author Артём
 *
 */
public interface IDevice {

	/**
	 * Идентификационный номер на плате.
	 * 
	 * @return
	 */
	public DigitalPin getDef();

	/**
	 * Возвращает наименование.
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Возвращает тип устройства.
	 * 
	 * @return
	 */
	public DeviceType getType();

	/**
	 * Режим работы.
	 * 
	 * @return
	 */
	public Boolean isNormaliClosed();

}
