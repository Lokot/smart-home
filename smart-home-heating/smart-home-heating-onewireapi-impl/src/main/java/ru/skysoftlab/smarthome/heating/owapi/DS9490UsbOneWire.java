package ru.skysoftlab.smarthome.heating.owapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.TemperatureContainer;
import com.dalsemi.onewire.utils.Address;

/**
 * Реализация интерфейса взаимодействия с 1-Wire сетью через нативные библиотеки
 * (DS9490 USB).
 * 
 * @author Артём
 *
 */
public class DS9490UsbOneWire implements IOneWire {

	private static final long serialVersionUID = -8884179885543393286L;

	/** DS18B20 writes data to scratchpad command */
	public static final byte DS18B20_FAMILY = (byte) 0x28;

	private static final String adapter_name = "DS9490";
	private static final String port_name = "USB1";

	private DSPortAdapter access = null;

	public DS9490UsbOneWire(String aAdapter_name, String aPort_name) {
		if (aAdapter_name == null) {
			aAdapter_name = adapter_name;
		}
		if (aPort_name == null) {
			aPort_name = port_name;
		}
		try {
			access = OneWireAccessProvider.getAdapter(aAdapter_name, aPort_name);
		} catch (OneWireException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Float getTemperature(String id) throws IOException {
		if (Address.isValid(id)) {
			OneWireContainer owc = access.getDeviceContainer(id);
			if (owc instanceof TemperatureContainer) {
				try {
					return readTemp(owc);
				} catch (OneWireException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.out.println(id + "not valid");
		}
		return null;
	}

	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		if (Address.isValid(id)) {
			OneWireContainer owc = access.getDeviceContainer(id);
			if (owc instanceof TemperatureContainer) {
				TemperatureContainer tc = null;
				try {
					tc = (TemperatureContainer) owc;
					byte[] state = tc.readDevice();
					tc.setTemperatureAlarm(TemperatureContainer.ALARM_HIGH, temp, state);
					tc.writeDevice(state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println(id + "not valid");
		}
	}

	@Override
	public void setLowTemp(String id, Float temp) throws IOException {
		if (Address.isValid(id)) {
			OneWireContainer owc = access.getDeviceContainer(id);
			if (owc instanceof TemperatureContainer) {
				TemperatureContainer tc = null;
				try {
					tc = (TemperatureContainer) owc;
					byte[] state = tc.readDevice();
					tc.setTemperatureAlarm(TemperatureContainer.ALARM_LOW, temp, state);
					tc.writeDevice(state);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println(id + "not valid");
		}
	}

	@Override
	public Float getFasttemp(String id) throws IOException {
		return getTemperature(id);
	}

	@Override
	public List<String> getIdsDS18B() throws IOException {
		List<String> rv = new ArrayList<>();
		try {
			access.adapterDetected();
			// access.targetAllFamilies();
			// access.beginExclusive(true);
			access.reset();
			access.setSearchAllDevices();
			boolean next = access.findFirstDevice();

			if (!next) {
				System.out.println("Could not find any iButtons!");
				return rv;
			}

			while (next) {
				OneWireContainer tc = access.getDeviceContainer();
				if (tc instanceof TemperatureContainer) {
					rv.add(tc.getAddressAsString());
				}
				next = access.findNextDevice();
			}

		} catch (OneWireException e1) {
			e1.printStackTrace();
		}
		return rv;
	}

	@Override
	public List<String> getAlarmed() throws IOException {
		List<String> rv = new ArrayList<>();
		try {
			access.adapterDetected();
			// access.targetAllFamilies();
			// access.beginExclusive(true);
			access.reset();
			access.setSearchAllDevices();
			boolean next = access.findFirstDevice();

			if (!next) {
				System.out.println("Could not find any iButtons!");
				return rv;
			}

			while (next) {
				OneWireContainer tc = access.getDeviceContainer();
				if (tc instanceof TemperatureContainer && tc.isAlarming()) {
					rv.add(tc.getAddressAsString());
				}
				next = access.findNextDevice();
			}

		} catch (OneWireException e1) {
			e1.printStackTrace();
		}
		return rv;
	}

	/** print out Exception stack trace */
	private void printException(Exception e) {
		System.out.println("***** EXCEPTION *****");
		e.printStackTrace();
	}

	/** clean up before exiting program */
	private void cleanup() {
		try {
			if (access != null) {
				access.endExclusive(); // end exclusive use of adapter
				access.freePort(); // free port used by adapter
			}
		} catch (Exception e) {
			printException(e);
		}

		return;
	}

	@Override
	public void close() throws IOException {
		cleanup();
	}

	@Override
	public Map<String, Float> getAlarmedTemps() throws IOException {
		Map<String, Float> rv = new HashMap<>();
		try {
			access.adapterDetected();
			// access.targetAllFamilies();
			// access.beginExclusive(true);
			access.reset();
			access.setSearchAllDevices();
			boolean next = access.findFirstDevice();

			if (!next) {
				System.out.println("Could not find any iButtons!");
				return rv;
			}

			while (next) {
				OneWireContainer owc = access.getDeviceContainer();
				if (owc instanceof TemperatureContainer && owc.isAlarming()) {
					rv.put(owc.getAddressAsString(), readTemp(owc));
				}
				next = access.findNextDevice();
			}

		} catch (OneWireException e1) {
			e1.printStackTrace();
		}
		return rv;
	}

	private float readTemp(OneWireContainer owc) throws OneWireException {
		TemperatureContainer tc = (TemperatureContainer) owc;
		byte[] state = tc.readDevice();
		// try {
		// tc.doTemperatureConvert(state);
		// } catch (Exception e) {
		// System.out.println("= Could not complete temperature conversion...");
		// }
		// state = tc.readDevice();
		double temp = tc.getTemperature(state);
		return (float) temp;
	}

}
