package ru.skysoftlab.smarthome.heating.ui;

import java.util.HashMap;
import java.util.Map;

import ru.skysoftlab.smarthome.heating.ejb.SensorEntityProviderBean;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.ui.forms.SensorsForm;

import com.vaadin.data.Container.Indexed;

/**
 * 
 * @author Loktionov Artem
 *
 */
public class SensorsView extends
		AbstractGridView<Sensor, SensorsForm, SensorEntityProviderBean> {

	private static final long serialVersionUID = 6698245813955647506L;

	public static final String NAME = "sensors";

	public SensorsView() {
		super(Sensor.class, SensorsForm.class, SensorEntityProviderBean.class);
	}

	@Override
	protected Indexed refreshData(String stringFilter) {
		// TODO вставить фильтрацию
		// contactList.setContainerDataSource(new BeanItemContainer<>(
		// Sensor.class, service.findAll(stringFilter)));
		return getJpaContainer();
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый датчик";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "id", "gpioPin", "deviceName"};
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "name", "sensorId", "low", "top" };
	}
	
	@Override
	protected String getTitle() {
		return "Список датчиков";
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("sensorId", "Идентификатор датчика");
		rv.put("name", "Наименование");
		rv.put("low", "Минимум (C)");
		rv.put("top", "Максимум (C)");
		return rv;
	}

}
