package ru.skysoftlab.smarthome.heating.gpio;

/**
 * Пин на материнской плате.
 * 
 * @author Артём
 *
 */
public interface IGpioPin {

	/**
	 * Виртуальный номер пина.
	 * 
	 * @return
	 */
	public Integer getGpio();

	/**
	 * Возвращает наименование.
	 * 
	 * @return
	 */
	public String getName();

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
	public GpioPinType getType();

}
