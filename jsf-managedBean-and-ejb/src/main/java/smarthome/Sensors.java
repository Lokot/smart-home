package smarthome;

import smarthome.gpio.GpioPins;

public enum Sensors {
	Bathroom(0, "28.BC8533040000", "Ванная", new GpioPins[] { GpioPins.Bathroom }), Hallway(1, "28.76E830040000",
			"Коридор", new GpioPins[] { GpioPins.Hallway }), Livingroom(2, "28.8AF530040000", "Зал", new GpioPins[] {
			GpioPins.Livingroom, GpioPins.Badroom }), Balcony(3, "28.F4E330040000", "Балкон",
			new GpioPins[] { GpioPins.Balcony });

	private final int index;
	private final String id;
	private final String name;
	private final GpioPins[] gpioPin;

	private Sensors(int index, String id, String name, GpioPins[] gpioPin) {
		this.index = index;
		this.id = id;
		this.name = name;
		this.gpioPin = gpioPin;
	}

	public int getIndex() {
		return this.index;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public GpioPins[] getGpioPins() {
		return this.gpioPin;
	}
}