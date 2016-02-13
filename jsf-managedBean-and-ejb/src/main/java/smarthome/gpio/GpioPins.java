package smarthome.gpio;

import java.io.IOException;

public enum GpioPins {

	Livingroom(6, 8, "PC22", "Зал", false),
	Hallway(4, 6, "PC21", "Прихожая", false),
	Badroom(8, 19, "PI3", "Спальня", false), 
	Balcony(7, 21, "PB2", "Балкон", false), 
	Bathroom(3, 5, "PC19", "Ванная", false), 
	Boiler(5, 7, "PC20", "Котел", true);

	private final int gpio;
	private final int pin;
	private final String def;
	private final String name;
	private final boolean normaliClosed;

	private GpioPins(int aGpio, int aPin, String aDef, String aName, boolean aNC) {
		this.gpio = aGpio;
		this.pin = aPin;
		this.def = aDef;
		this.name = aName;
		this.normaliClosed = aNC;
	}

	public Integer getGpio() {
		return Integer.valueOf(this.gpio);
	}

	public String toString() {
		return "gpio" + this.gpio;
	}

	public boolean getState() throws IOException {
		return Pins.isEnabledPin(this);
	}

	public void setOn() throws IOException {
		Pins.setPinHigh(this);
	}

	public void setOff() throws IOException {
		Pins.setPinLow(this);
	}

	public String getName() {
		return this.name;
	}

	public boolean isNormaliClosed() {
		return this.normaliClosed;
	}
}
