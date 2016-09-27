package ru.skysoftlab.smarthome.heating.owapi;

import java.io.IOException;
import java.util.List;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.TemperatureContainer;

import ru.skysoftlab.smarthome.heating.onewire.IOneWire;

public class OWApiWrapper  implements IOneWire{

	private static final long serialVersionUID = -8884179885543393286L;

	@Override
	public Float getTemperature(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		try {
			TemperatureContainer tc = null;
			byte[] state = tc.readDevice();
			tc.setTemperatureAlarm(tc.ALARM_HIGH, 28.0, state);
		} catch (OneWireException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
