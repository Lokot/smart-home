package ru.skysoftlab.smarthome.heating.onewire;

import java.io.Serializable;
import java.util.List;

/**
 * Интерфейс сканера датчиков.
 * 
 * @author Артём
 *
 */
public interface IAlarmDevScanner extends Serializable {

	/**
	 * Сканирует датчики находящиеся в тревожном состоянии.
	 */
	public void scanAlarmingDevices();

	/**
	 * Устанавливает настройки.
	 * 
	 * @param config
	 */
	public void setDeviceConfig(IDs18bConfig config);

	/**
	 * Устанавливает настройки.
	 * 
	 * @param configs
	 */
	public void setDeviceConfigs(List<IDs18bConfig> configs);
}
