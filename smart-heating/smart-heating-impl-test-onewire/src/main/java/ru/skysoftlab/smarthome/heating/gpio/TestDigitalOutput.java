package ru.skysoftlab.smarthome.heating.gpio;

import io.silverspoon.bulldog.core.Signal;
import io.silverspoon.bulldog.core.gpio.base.AbstractDigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;

public class TestDigitalOutput extends AbstractDigitalOutput {

	public TestDigitalOutput(Pin pin) {
		super(pin);
	}

	@Override
	protected void applySignalImpl(Signal signal) {
		System.out.println(getPin().getName() + " = " + signal);
	}

	@Override
	protected void setupImpl() {

	}

	@Override
	protected void teardownImpl() {

	}

}
