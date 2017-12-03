package ru.skysoftlab.smarthome.heating.gpio;

import io.silverspoon.bulldog.core.Signal;
import io.silverspoon.bulldog.core.gpio.base.AbstractDigitalInput;
import io.silverspoon.bulldog.core.pin.Pin;

public class TestDigitalInput extends AbstractDigitalInput {

	public TestDigitalInput(Pin pin) {
		super(pin);
	}

	@Override
	public Signal read() {
		return null;
	}

	@Override
	protected void enableInterruptsImpl() {
	}

	@Override
	protected void disableInterruptsImpl() {
	}

	@Override
	protected void setupImpl() {
	}

	@Override
	protected void teardownImpl() {
	}

}
