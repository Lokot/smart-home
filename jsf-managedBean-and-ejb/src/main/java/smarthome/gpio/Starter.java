package smarthome.gpio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Starter implements Runnable {
	public void run() {
		for (GpioPins gpioPin : GpioPins.values()) {
			try {
				Pins.exportPin(gpioPin);
				FileOutputStream fileOutputStream = new FileOutputStream(new File("/sys/class/gpio/"
						+ gpioPin.toString() + "/direction"));
				fileOutputStream.write("out".getBytes());
				fileOutputStream.flush();
				fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}