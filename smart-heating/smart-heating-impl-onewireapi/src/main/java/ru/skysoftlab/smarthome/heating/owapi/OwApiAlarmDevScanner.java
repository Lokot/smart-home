package ru.skysoftlab.smarthome.heating.owapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner;
import ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig;
import ru.skysoftlab.smarthome.heating.onewire.IOneWire;
import ru.skysoftlab.smarthome.heating.onewire.ITempAlarmHandler;
import ru.skysoftlab.smarthome.heating.onewire.TempAlarmingEvent;

/**
 * Сканер датчиков для DS9490.
 * 
 * @author Артём
 *
 */
public class OwApiAlarmDevScanner implements IAlarmDevScanner {

	private static final long serialVersionUID = -5251182044681739574L;

	private IOneWire oneWire;
	@Inject
	private final ITempAlarmHandler alarmHandler;
	private Map<String, IDs18bConfig> configs = new HashMap<>();

	public OwApiAlarmDevScanner(ITempAlarmHandler aAlarmHandler) {
		alarmHandler = aAlarmHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#scanAlarmingDevices
	 * ()
	 */
	@Override
	public void scanAlarmingDevices() {
		try {
			Map<String, Float> alarmedTemps = oneWire.getAlarmedTemps();
			for (String id : alarmedTemps.keySet()) {
				float alarmedTemp = alarmedTemps.get(id);
				if (alarmedTemp <= configs.get(id).getLow()) {
					// ON
					alarmHandler.handleAlarm(TempAlarmingEvent.createLowTempEvent(id, alarmedTemp));
				} else if (alarmedTemp >= configs.get(id).getTop()) {
					// OFF
					alarmHandler.handleAlarm(TempAlarmingEvent.createTopTempEvent(id, alarmedTemp));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#setDeviceConfig
	 * (ru.skysoftlab.smarthome.heating.onewire.IDs18bConfig)
	 */
	@Override
	public void setDeviceConfig(IDs18bConfig config) {
		configs.put(config.getDeviceName(), config);
		try {
			oneWire.setHighTemp(config.getDeviceName(), config.getTop());
			oneWire.setLowTemp(config.getDeviceName(), config.getLow());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.onewire.IAlarmDevScanner#setDeviceConfigs
	 * (java.util.List)
	 */
	@Override
	public void setDeviceConfigs(List<IDs18bConfig> configs) {
		for (IDs18bConfig iDs18bConfig : configs) {
			setDeviceConfig(iDs18bConfig);
		}
	}

}
