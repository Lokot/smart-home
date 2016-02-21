package ru.skysoftlab.smarthome.heating.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ru.skysoftlab.smarthome.heating.gpio.IGpioController;
import ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler;
import ru.skysoftlab.smarthome.heating.owfs.TempAlarmingEvent;

/**
 * Обработчик изменения температуры.
 * 
 * @author Артём
 *
 */
public class TempAlarmHandler implements ITempAlarmHandler, Serializable {

	private static final long serialVersionUID = 2760989956318314998L;
	/** Открыто. */
	private static final boolean OPEN = true;
	/** Закрыто. */
	private static final boolean CLOSE = false;
	/** Контроллер устройств. */
	private IGpioController gpioController;
	/** Состояния устройств. */
	private Map<String, Boolean> states = new HashMap<>();
	
	public TempAlarmHandler(IGpioController gpioController) {
		this.gpioController = gpioController;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.owfs.ITempAlarmHandler#handleAlarm(ru
	 * .skysoftlab.smarthome.heating.owfs.TempAlarmingEvent)
	 */
	@Override
	public void handleAlarm(TempAlarmingEvent event) {
		Boolean isOpen = states.get(event.getDeviceName());
		boolean isSwitch = false;
		switch (event.getType()) {
		case LOW:
			// если нет в списке или закрыт
			if (isOpen == null || !isOpen) {
				// открыть контур
				gpioController.toOpen(event.getDeviceName());
				states.put(event.getDeviceName(), OPEN);
				isSwitch = true;
			}
			break;

		case TOP:
		default:
			// если нет в списке или открыт
			if (isOpen == null || isOpen) {
				// закрыть контур
				gpioController.toClose(event.getDeviceName());
				states.put(event.getDeviceName(), CLOSE);
				isSwitch = true;
			}
			break;
		}

		// если были изменения
		if (isSwitch) {
			// запуск или останов
			if (isStartBoiler()) {
				gpioController.boilerOn();
			} else {
				gpioController.boilerOff();
				gpioController.toOpenAll();
			}
		}
	}

	/**
	 * Запускать котел.
	 * 
	 * @return
	 */
	private boolean isStartBoiler() {
		boolean rv = false;
		for (Boolean state : states.values()) {
			rv |= state;
		}
		return rv;
	}

}