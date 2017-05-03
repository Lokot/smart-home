package ru.skysoftlab.smarthome.heating.impl;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.Board;
import io.silverspoon.bulldog.core.platform.Platform;

import java.util.Collection;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.devices.IDevice;
import ru.skysoftlab.smarthome.heating.devices.IDevicesController;
import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.entitys.Valve;

/**
 * Управляет устройствами.
 * 
 * @author Артём
 *
 */
public class GpioController implements IDevicesController {

	private static final long serialVersionUID = -7828970261921394602L;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	private Board board = Platform.createBoard();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpen(java.lang
	 * .String)
	 */
	@Override
	public void openHC(String deviceName) {
		for (Valve valve : sensorsProvider.getDs18bConfig(deviceName)
				.getGpioPin()) {
			swichState(valve, valve.isNormaliClosed());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toClose(java.lang
	 * .String)
	 */
	@Override
	public void closeHC(String deviceName) {
		for (Valve valve : sensorsProvider.getDs18bConfig(deviceName)
				.getGpioPin()) {
			swichState(valve, !valve.isNormaliClosed());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOn()
	 */
	@Override
	public void boilerOn() {
		for (Boiler boiler : sensorsProvider.getBoilerGpioPin()) {
			swichState(boiler, boiler.isNormaliClosed());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOff()
	 */
	@Override
	public void boilerOff() {
		for (Boiler boiler : sensorsProvider.getBoilerGpioPin()) {
			swichState(boiler, !boiler.isNormaliClosed());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpenAll()
	 */
	@Override
	public void openHCAll() {
		for (Valve valve : sensorsProvider.getAllKonturs()) {
			swichState(valve, valve.isNormaliClosed());
		}
	}

	private void swichState(IDevice pin, boolean state) {
		Pin ppin = board.getPin(pin.getDef());
		DigitalOutput output = ppin.as(DigitalOutput.class);
		if (state) {
			output.high();
		} else {
			output.low();
		}
	}

	@Override
	public String[] getPinNames() {
		Collection<Pin> pins = board.getPins();
		String[] rv = new String[pins.size()];
		int i = 0;
		for (Pin pin : pins) {
			rv[i] = pin.getName();
			i++;
		}
		return rv;
	}

}
