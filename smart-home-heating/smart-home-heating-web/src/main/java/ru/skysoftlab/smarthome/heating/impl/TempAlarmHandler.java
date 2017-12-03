package ru.skysoftlab.smarthome.heating.impl;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.devices.IDevicesController;
import ru.skysoftlab.smarthome.heating.onewire.ITempAlarmHandler;
import ru.skysoftlab.smarthome.heating.onewire.TempAlarmingEvent;

/**
 * Обработчик изменения температуры.
 * 
 * @author Артём
 *
 */
public class TempAlarmHandler implements ITempAlarmHandler {

	private static final long serialVersionUID = 2760989956318314998L;
	/** Контроллер устройств. */
	@Inject
	private IDevicesController gpioController;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler#handleAlarm(ru
	 * .skysoftlab.smarthome.heating.owfs.TempAlarmingEvent)
	 */
	@Override
	public void handleAlarm(TempAlarmingEvent event) {

		switch (event.getType()) {
		case LOW:
			// открыть контур
			gpioController.openHC(event.getSensorId());
			break;

		case TOP:
		default:
			// закрыть контур
			gpioController.closeHC(event.getSensorId());
			break;
		}
	}

}
