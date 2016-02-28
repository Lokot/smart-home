package ru.skysoftlab.smarthome.heating.impl;

import java.io.IOException;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.gpio.IGpioController;
import ru.skysoftlab.smarthome.heating.gpio.IGpioPin;
import ru.skysoftlab.smarthome.heating.util.PinsUtil;

/**
 * Управляет устройствами.
 * 
 * @author Артём
 *
 */
public class GpioController implements IGpioController {

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
	public void toOpen(String deviceName) {
		for (IGpioPin gpioPin : sensorsProvider.getDs18bConfig(deviceName)
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
	public void toClose(String deviceName) {
		for (IGpioPin gpioPin : sensorsProvider.getDs18bConfig(deviceName)
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
	public void toOpenAll() {
		for (IGpioPin gpioPin : sensorsProvider.getAllKonturs()) {
			try {
				swichState(gpioPin, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void open(IGpioPin pin) throws IOException {
		swichState(pin, true);
	}

	private void close(IGpioPin pin) throws IOException {
		swichState(pin, false);
	}

	private void swichState(IGpioPin pin, boolean state) throws IOException {
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
