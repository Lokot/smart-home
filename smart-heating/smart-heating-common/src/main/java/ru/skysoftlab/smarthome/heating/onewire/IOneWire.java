package ru.skysoftlab.smarthome.heating.onewire;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс взаимодействия с 1-Wire сетью.
 * 
 * @author Артём
 *
 */
public interface IOneWire extends Serializable, Closeable {

	/**
	 * Возвращает температуру с датчика.
	 * 
	 * @param id
	 * @return
	 */
	public Float getTemperature(String id) throws IOException;

	/**
	 * Устанавливает максимальную температуру.
	 */
	public void setHighTemp(String id, Float temp) throws IOException;

	/**
	 * Устанавливает минимальную температуру.
	 */
	public void setLowTemp(String id, Float temp) throws IOException;

	/**
	 * Возвращает быструю температуру.
	 */
	public Float getFasttemp(String id) throws IOException;

	/**
	 * Возвращает список датчиков.
	 */
	public List<String> getIdsDS18B() throws IOException;

	/**
	 * Возвращает список сигнализирующих датчиков.
	 */
	public List<String> getAlarmed() throws IOException;
	
	/**
	 * Возвращает список сигнализирующих датчиков с температурой.
	 */
	public Map<String, Float> getAlarmedTemps() throws IOException;

}
