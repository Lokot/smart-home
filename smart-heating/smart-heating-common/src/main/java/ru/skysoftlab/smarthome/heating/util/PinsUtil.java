package ru.skysoftlab.smarthome.heating.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import ru.skysoftlab.smarthome.heating.gpio.IGpioPin;

public final class PinsUtil {

	public static final String gpio_path = "/sys/class/gpio";
	public static final String High = "1";
	public static final String Low = "0";
	public static final String Out = "out";

	public static void exportPin(IGpioPin gpioPin) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				gpio_path + "/export"));
		fileOutputStream.write(gpioPin.getGpio().toString().getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void unexportPin(IGpioPin gpioPin) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				gpio_path + "/unexport"));
		fileOutputStream.write(gpioPin.getGpio().toString().getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void setOutDirectionPin(IGpioPin gpioPin) throws IOException {
		setDirectionPin(gpioPin, "out");
	}

	private static void setDirectionPin(IGpioPin gpioPin, String type)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				gpio_path + "/gpio" + gpioPin.getGpio() + "/direction"));
		fileOutputStream.write(type.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
	}

	public static void setPinHigh(IGpioPin gpioPin) throws IOException {
		setPin(gpioPin, High);
	}

	public static void setPinLow(IGpioPin gpioPin) throws IOException {
		setPin(gpioPin, Low);
	}

	private static void setPin(IGpioPin gpioPin, String value)
			throws IOException {
		File gpioValue = new File(gpio_path + "/gpio" + gpioPin.getGpio()
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

	// TODO подумать про нормально-открытые и закрытые
	public static Boolean isEnabledPin(IGpioPin gpioPin) throws IOException {
		boolean rv = false;
		File gpioValue = new File(gpio_path + "/gpio" + gpioPin.getGpio()
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