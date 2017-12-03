package ru.skysoftlab.smarthome.heating.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.inject.Inject;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.Board;
import ru.skysoftlab.gpio.DigitalPin;
import ru.skysoftlab.smarthome.heating.devices.IDevicesController;
import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Valve;

/**
 * Управляет устройствами.
 * 
 * @author Артём
 *
 */
public class GpioController implements IDevicesController {

	/** Закрыто. */
	private static final boolean CLOSE = false;
	/** Открыто. */
	private static final boolean OPEN = true;
	private static final long serialVersionUID = -7828970261921394602L;

	@Inject
	private Board board;

	public Map<String, Sensor> sensors = new HashMap<>();
	@Inject
	private SensorsAndDevicesProvider sensorsProvider;
	public Table<Boiler, Valve, Boolean> statesNew = HashBasedTable.create();

	private void boilerOff(Boiler boiler) {
		swichState(boiler.getDef(), !boiler.isNormaliClosed());
		// TODO удалить
		System.out.println("boiler off");
	}

	private void boilerOn(Boiler boiler) {
		swichState(boiler.getDef(), boiler.isNormaliClosed());
		// TODO удалить
		System.out.println("boiler on");
	}

	private void checkStates() {
		for (Boiler boiler : statesNew.rowKeySet()) {
			boolean newState = CLOSE;
			for (Boolean valveState : statesNew.row(boiler).values()) {
				newState |= valveState;
			}
			if (newState) {
				boilerOn(boiler);
			} else {
				boilerOff(boiler);
				openHCAll(boiler);
			}
		}
	}

	private void closeHC(Sensor sensor) {
		for (Valve valve : sensor.getGpioPin()) {
			switch (valve.getValveState()) {
			case AUTO:
			case CLOSE_ALL:
				closeValve(sensor.getMaster(), valve);
				break;

			case OPEN_ALL:
				openValve(sensor.getMaster(), valve);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#toClose(java.lang
	 * .String)
	 */
	@Override
	// TODO add transaction
	public void closeHC(String sensorId) {
		Sensor sensor = getSensor(sensorId);
		closeHC(sensor);
		checkStates();
	}

	private void closeValve(Boiler boiler, Valve valve) {
		statesNew.put(boiler, valve, CLOSE);
		swichState(valve.getDef(), !valve.isNormaliClosed());
		// TODO удалить
		System.out.println("close");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.devices.IDevicesController#getPinNames()
	 */
	@Override
	public Collection<DigitalPin> getPinNames() {
		Collection<DigitalPin> rv = new HashSet<>();
		Collection<Pin> pins = board.getPins();
		for (Pin pin : pins) {
			rv.add(new DigitalPin(pin.getName()));
		}
		return rv;
	}

	private Sensor getSensor(String sensorId) {
		Sensor sensor = sensors.get(sensorId);
		if (sensor == null) {
			sensor = sensorsProvider.getDs18bConfig(sensorId);
			sensors.put(sensorId, sensor);
		}
		return sensor;
	}

	private void openHC(Sensor sensor) {
		for (Valve valve : sensor.getGpioPin()) {
			switch (valve.getValveState()) {
			case AUTO:
			case OPEN_ALL:
				openValve(sensor.getMaster(), valve);
				break;

			case CLOSE_ALL:
				closeValve(sensor.getMaster(), valve);
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpen(java.lang
	 * .String)
	 */
	@Override
	// TODO add transaction
	public void openHC(String sensorId) {
		Sensor sensor = getSensor(sensorId);
		openHC(sensor);
		checkStates();
	}

	private void openHCAll(Boiler boiler) {
		for (Sensor sensor : boiler.getSensors()) {
			openHC(sensor);
		}
	}

	private void openValve(Boiler boiler, Valve valve) {
		statesNew.put(boiler, valve, OPEN);
		swichState(valve.getDef(), valve.isNormaliClosed());
		// TODO удалить
		System.out.println("open");
	}

	private void swichState(DigitalPin dPin, boolean state) {
		Pin pin = board.getPin(dPin.getName());
		DigitalOutput output = pin.as(DigitalOutput.class);
		if (state) {
			output.high();
		} else {
			output.low();
		}
	}

}
