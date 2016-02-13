package smarthome.gpio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public final class Pins {
	
	public static final String gpio_path = "/sys/class/gpio";
	public static final String High = "1";
	public static final String Low = "0";
	public static final String Out = "out";

	public static void exportPin(GpioPins gpioPin) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				"/sys/class/gpio/export"));
		fileOutputStream.write(gpioPin.getGpio().toString().getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void unexportPin(GpioPins gpioPin) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				"/sys/class/gpio/unexport"));
		fileOutputStream.write(gpioPin.getGpio().toString().getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void setOutDirectionPin(GpioPins gpioPin) throws IOException {
		setDirectionPin(gpioPin, "out");
	}

	private static void setDirectionPin(GpioPins gpioPin, String type)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				"/sys/class/gpio/" + gpioPin.toString() + "/direction"));
		fileOutputStream.write(type.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void setPinHigh(GpioPins gpioPin) throws IOException {
		setPin(gpioPin, "1");
	}

	public static void setPinLow(GpioPins gpioPin) throws IOException {
		setPin(gpioPin, "0");
	}

	private static void setPin(GpioPins gpioPin, String value)
			throws IOException {
		File gpioValue = new File("/sys/class/gpio/" + gpioPin.toString()
				+ "/value");
		if (gpioValue.exists()) {
			FileOutputStream fileOutputStream = new FileOutputStream(gpioValue);
			fileOutputStream.write(value.getBytes());
			fileOutputStream.flush();
			fileOutputStream.close();
		} else {
			throw new IOException("Пин не создан.");
		}
	}

	public static boolean isEnabledPin(GpioPins gpioPin) throws IOException {
		boolean rv = false;
		File gpioValue = new File("/sys/class/gpio/" + gpioPin.toString()
				+ "/value");
		if (gpioValue.exists()) {
			FileReader fr = new FileReader(gpioValue);
			char[] a = new char[1];
			fr.read(a, 0, 1);
			if (a[0] == '1') {
				rv = true;
			}
			fr.close();
			return rv;
		}
		throw new IOException("Пин не создан.");
	}
}