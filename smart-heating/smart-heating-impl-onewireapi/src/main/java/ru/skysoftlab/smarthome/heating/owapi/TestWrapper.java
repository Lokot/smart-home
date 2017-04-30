package ru.skysoftlab.smarthome.heating.owapi;

import java.io.IOException;

import cz.adamh.utils.NativeUtils;

public class TestWrapper {

	public static void main(String[] args) throws IOException {
//		try {
////			System.loadLibrary("onewireUSB");
//			NativeUtils.loadLibraryFromJar("/libs/libonewireUSB.so");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		DS9490UsbOneWire wrapper = new DS9490UsbOneWire(null, null);
		System.out.println("All ---------------------------------------------");
		for (String id : wrapper.getIdsDS18B()) {
			System.out.println("Идентификатор = " + id + " температура = " + wrapper.getTemperature(id));
		}
		System.out.println("Set alarm min temp 24.9 and max temp to 30-------------------------");
		for (String id : wrapper.getIdsDS18B()) {
			wrapper.setHighTemp(id, 30.0f);
			wrapper.setLowTemp(id, 24.9f);
		}
		System.out.println("Alarmed ------------------------------------------");
		for (String id : wrapper.getAlarmed()) {
			System.out.println("Идентификатор = " + id + " температура = " + wrapper.getTemperature(id));
		}
		wrapper.close();
	}

}
