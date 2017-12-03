package ru.skysoftlab.smarthome.heating.onewire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

@Singleton
public class TestImplOneWire implements IOneWire {

	private static final long serialVersionUID = -3454045540454990306L;

	private Map<String, TestDs18> dsMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 5; i++) {
			TestDs18 ds = new TestDs18();
			ds.setId("testId_" + i);
			ds.setLowTemp(15f);
			ds.setMaxTemp(25f);
			ds.setTemp(17f + i);
			dsMap.put(ds.getId(), ds);
		}
	}

	@Override
	public void close() throws IOException {
		dsMap = null;
	}

	@Override
	public Float getTemperature(String id) throws IOException {
		return dsMap.get(id).getTemp();
	}

	@Override
	public void setHighTemp(String id, Float temp) throws IOException {
		dsMap.get(id).setMaxTemp(temp);
	}

	@Override
	public void setLowTemp(String id, Float temp) throws IOException {
		dsMap.get(id).setLowTemp(temp);
	}

	@Override
	public Float getFasttemp(String id) throws IOException {
		return dsMap.get(id).getTemp();
	}

	@Override
	public List<String> getIdsDS18B() throws IOException {
		return new ArrayList<>(dsMap.keySet());
	}

	@Override
	public List<String> getAlarmed() throws IOException {
		List<String> rv = new ArrayList<>();
		for (TestDs18 ds : dsMap.values()) {
			if (ds.isAlarmed()) {
				rv.add(ds.getId());
			}
		}
		return rv;
	}

	@Override
	public Map<String, Float> getAlarmedTemps() throws IOException {
		Map<String, Float> rv = new HashMap<>();
		for (TestDs18 ds : dsMap.values()) {
			if (ds.isAlarmed()) {
				rv.put(ds.getId(), ds.getTemp());
			}
		}
		return rv;
	}

}
