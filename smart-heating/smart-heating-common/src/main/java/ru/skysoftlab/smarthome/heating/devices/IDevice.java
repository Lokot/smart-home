package ru.skysoftlab.smarthome.heating.devices;

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
	public String getDef();

	/**
	 * Возвращает наименование.
	 * 
	 * @return
	 */
	public String getUserName();

	/**
	 * Режим работы.
	 * 
	 * @return
	 */
	public Boolean isNormaliClosed();

	/**
	 * Возвращает тип устройства.
	 * 
	 * @return
	 */
	public DeviceType getType();
	
	
}
