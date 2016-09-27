package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.devices.IDevicesController;
import ru.skysoftlab.smarthome.heating.devices.IDevice;
import ru.skysoftlab.smarthome.heating.util.PinsUtil;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpen(java.lang
	 * .String)
	 */
	@Override
	public void openHC(String deviceName) {
		for (IDevice gpioPin : sensorsProvider.getDs18bConfig(deviceName)
				.getGpioPin()) {
			try {
				open(gpioPin);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		for (IDevice gpioPin : sensorsProvider.getDs18bConfig(deviceName)
				.getGpioPin()) {
			try {
				close(gpioPin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOn()
	 */
	@Override
	public void boilerOn() {
		try {
			open(sensorsProvider.getBoilerGpioPin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#boilerOff()
	 */
	@Override
	public void boilerOff() {
		try {
			close(sensorsProvider.getBoilerGpioPin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ru.skysoftlab.smarthome.heating.gpio.IGpioController#toOpenAll()
	 */
	@Override
	public void openHCAll() {
		for (IDevice gpioPin : sensorsProvider.getAllKonturs()) {
			try {
				swichState(gpioPin, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void open(IDevice pin) throws IOException {
		swichState(pin, true);
	}

	private void close(IDevice pin) throws IOException {
		swichState(pin, false);
	}

	private void swichState(IDevice pin, boolean state) throws IOException {
		if ((pin.isNormaliClosed() && state)
				|| (!pin.isNormaliClosed() && !state)) {
			if (!PinsUtil.isEnabledPin(pin)) {
				PinsUtil.setPinHigh(pin);
			}
		} else {
			if (PinsUtil.isEnabledPin(pin)) {
				PinsUtil.setPinLow(pin);
			}
		}
	}

}
