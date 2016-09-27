package ru.skysoftlab.smarthome.heating.owapi;

import java.io.IOException;
import java.util.List;

import com.dalsemi.onewire.OneWireAccessProvider;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.TemperatureContainer;

import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

public class OWApiWrapper implements IOneWire {

	private static final long serialVersionUID = -8884179885543393286L;
	
	/** DS18B20 writes data to scratchpad command */
	public static final byte DS18B20_FAMILY = ( byte ) 0x28;

	private static final String adapter_name = "DS9490";
	private static final String port_name = "USB1";

	@Override
	public Float getTemperature(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		DSPortAdapter access = null;
		try {
			access = OneWireAccessProvider.getAdapter(adapter_name, port_name);
			access.adapterDetected();
			// TODO указать тип
			// access.targetAllFamilies();
			access.targetFamily(DS18B20_FAMILY);
			
			access.beginExclusive(true);
			access.reset();
			// TODO выбрать датчик
			// access.setSearchAllDevices();
			access.select(id);
			OneWireContainer owc = access.getDeviceContainer();
			TemperatureContainer tc = null;
			try {
				tc = (TemperatureContainer) owc;

				byte[] state = tc.readDevice();
				tc.setTemperatureAlarm(TemperatureContainer.ALARM_HIGH, temp, state);
				tc.writeDevice(state);
			} catch (Exception e) {
				tc = null;
			}
		} catch (OneWireException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void setLowTemp(String id, Float temp) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Float getFasttemp(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIdsDS18B() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAlarmed() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
