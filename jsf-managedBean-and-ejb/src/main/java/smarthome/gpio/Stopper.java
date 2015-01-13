package smarthome.gpio;

import java.io.IOException;

public class Stopper implements Runnable {
	public void run() {
		for (GpioPins gpioPin : GpioPins.values()) {
			try {
				Pins.unexportPin(gpioPin);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
