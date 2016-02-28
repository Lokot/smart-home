package ru.skysoftlab.smarthome.heating.gpio;

import java.io.Serializable;

/**
 * Интерфейс контроллера пинов.
 * 
 * @author Артём
 *
 */
public interface IGpioController extends Serializable {

	/**
	 * Открывает контур.
	 * 
	 * @param deviceName
	 */
	public void toOpen(String deviceName);

	/**
	 * Закрывает контур.
	 * 
	 * @param deviceName
	 */
	public void toClose(String deviceName);

	/**
	 * Включает котел.
	 */
	public void boilerOn();

	/**
	 * Выключает котел.
	 */
	public void boilerOff();

	/**
	 * Открывает все контура.
	 */
	public void toOpenAll();

}
