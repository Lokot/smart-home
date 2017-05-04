package ru.skysoftlab.smarthome.heating.gpio;

import io.silverspoon.bulldog.core.gpio.base.DigitalIOFeature;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.AbstractBoard;
import io.silverspoon.bulldog.core.platform.Board;

import javax.enterprise.inject.Alternative;

@Alternative
public class BoardProducer extends AbstractBoard implements Board {

	public BoardProducer() {
		createPins();
	}

	private void createPins() {
		getPins().add(createSysFsPin("PB2", 7, "B", 21, "7", true));
		getPins().add(createSysFsPin("PC19", 3, "C", 5, "3", true));
		getPins().add(createSysFsPin("PC20", 5, "C", 7, "5", true));
		getPins().add(createSysFsPin("PC21", 4, "C", 6, "4", true));
		getPins().add(createSysFsPin("PC22", 6, "C", 8, "6", true));
		getPins().add(createSysFsPin("PI3", 8, "I", 19, "8", true));
	}

	public Pin createSysFsPin(String name, int address, String port,
			int indexOnPort, String fsName, boolean interrupt) {
		Pin pin = new Pin(name, address, port, indexOnPort);
		TestDigitalInput input = new TestDigitalInput(pin);
		TestDigitalOutput output = new TestDigitalOutput(pin);
		pin.addFeature(new DigitalIOFeature(pin, input, output));
		return pin;
	}

	@Override
	public String getName() {
		return "Test board";
	}

}
