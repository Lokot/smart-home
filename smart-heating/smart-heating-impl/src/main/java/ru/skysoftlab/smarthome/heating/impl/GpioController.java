package ru.skysoftlab.smarthome.heating.impl;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.Board;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.devices.IDevicesController;
import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Valve;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * Управляет устройствами.
 * 
 * @author Артём
 *
 */
public class GpioController implements IDevicesController {

	private static final long serialVersionUID = -7828970261921394602L;
	/** Открыто. */
	private static final boolean OPEN = true;
	/** Закрыто. */
	private static final boolean CLOSE = false;

	@Inject
	private SensorsAndDevicesProvider sensorsProvider;

	@Inject
	private Board board;
	public Map<String, Sensor> sensors = new HashMap<>();
	public Table<Boiler, Valve, Boolean> statesNew = HashBasedTable.create();

	private Sensor getSensor(String sensorId) {
		Sensor sensor = sensors.get(sensorId);
		if (sensor == null) {
			sensor = sensorsProvider.getDs18bConfig(sensorId);
		}
		return sensor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpen(java.lang
	 * .String)
	 */
	@Override
	// TODO add transaction
	public void openHC(String sensorId) {
		Sensor sensor = getSensor(sensorId);
		for (Valve valve : sensor.getGpioPin()) {
			statesNew.put(sensor.getMaster(), valve, OPEN);
			swichState(valve.getDef(), valve.isNormaliClosed());
		}
		checkStates();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toClose(java.lang
	 * .String)
	 */
	@Override
	// TODO add transaction
	public void closeHC(String sensorId) {
		Sensor sensor = getSensor(sensorId);
		for (Valve valve : sensor.getGpioPin()) {
			statesNew.put(sensor.getMaster(), valve, CLOSE);
			swichState(valve.getDef(), !valve.isNormaliClosed());
		}
		checkStates();
	}

	private void boilerOn(Boiler boiler) {
		swichState(boiler.getDef(), boiler.isNormaliClosed());
	}

	private void boilerOff(Boiler boiler) {
		swichState(boiler.getDef(), !boiler.isNormaliClosed());
	}

	private void openHCAll(Boiler boiler) {
		for (Sensor sensor : boiler.getSensors()) {
			for (Valve valve : sensor.getGpioPin()) {
				swichState(valve.getDef(), valve.isNormaliClosed());
				statesNew.put(boiler, valve, OPEN);
			}
		}
	}

	private void swichState(String pinName, boolean state) {
		Pin pin = board.getPin(pinName);
		DigitalOutput output = pin.as(DigitalOutput.class);
		if (state) {
			output.high();
		} else {
			output.low();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.devices.IDevicesController#getPinNames()
	 */
	@Override
	public Collection<String> getPinNames() {
		Collection<String> rv = new HashSet<>();
		Collection<Pin> pins = board.getPins();
		for (Pin pin : pins) {
			rv.add(pin.getName());
		}
		return rv;
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

}
