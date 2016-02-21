package ru.skysoftlab.smarthome.heating.gpio;

public interface IGpioController {
	/**
	 * Открывает контур.
	 * 
	 * @param deviceName 
	 */
	public void toOpen(String deviceName);

	public void toClose(String deviceName);

	public void boilerOn();

	public void boilerOff();

	public void toOpenAll();

}
