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

	public void scanAlarmingDevices();

	public void setDeviceConfig(IDs18bConfig config);

	public void setDeviceConfigs(List<IDs18bConfig> configs);
}
