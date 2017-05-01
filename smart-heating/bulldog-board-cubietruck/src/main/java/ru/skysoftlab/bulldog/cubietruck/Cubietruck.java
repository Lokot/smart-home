package ru.skysoftlab.bulldog.cubietruck;

import io.silverspoon.bulldog.core.gpio.base.DigitalIOFeature;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.AbstractBoard;
import io.silverspoon.bulldog.linux.gpio.LinuxDigitalInput;
import io.silverspoon.bulldog.linux.gpio.LinuxDigitalOutput;
import io.silverspoon.bulldog.linux.util.LinuxLibraryLoader;

public class Cubietruck extends AbstractBoard {
	private static final String NAME = "Cubietruck";
	private static Cubietruck instance;

	public static Cubietruck getInstance() {
		if (instance == null) {
			LinuxLibraryLoader.loadNativeLibrary(NAME.toLowerCase());
			instance = new Cubietruck();
		}

		return instance;
	}

	private Cubietruck() {
		super();
		createPins();
	}

	@Override
	public void cleanup() {
		super.cleanup();
	}

	@Override
	public String getName() {
		return NAME;
	}

	private void createPins() {
		
		getPins().add(createSysFsPin(CubietruckNames.PB2, 7, "C", 21, "7", true));
		
		getPins().add(createSysFsPin(CubietruckNames.PC19, 3, "C", 5, "3", true));
		getPins().add(createSysFsPin(CubietruckNames.PC20, 5, "C", 7, "5", true));
		getPins().add(createSysFsPin(CubietruckNames.PC21, 4, "C", 6, "4", true));
		getPins().add(createSysFsPin(CubietruckNames.PC22, 6, "C", 8, "6", true));

		getPins().add(createSysFsPin(CubietruckNames.PI3, 8, "C", 19, "8", true));
	}

	public Pin createSysFsPin(String name, int address, String port, int indexOnPort, String fsName, boolean interrupt) {
		CubietruckPin pin = new CubietruckPin(name, address, port, indexOnPort, fsName, interrupt);
		LinuxDigitalInput input = new LinuxDigitalInput(pin);
		LinuxDigitalOutput output = new LinuxDigitalOutput(pin);
		pin.addFeature(new DigitalIOFeature(pin, input, output));
		return pin;
	}
}
