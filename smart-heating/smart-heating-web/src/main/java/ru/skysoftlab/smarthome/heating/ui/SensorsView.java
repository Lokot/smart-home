package ru.skysoftlab.smarthome.heating.ui;

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
		return new String[] { "id", "gpioPin" };
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "sensorId", "name", "maxTemp" };
	}

}
