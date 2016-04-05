package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.ConfigMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.jpa.SensorEntityProviderBean;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.SensorsForm;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container.Indexed;

/**
 * Управление датчиками.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(NavigationService.SENSORS)
@MainMenuItem(name = "Настройки", order = MainMenu.CONFIG)
@MenuItemView(name = "Датчики", order = ConfigMenu.SENSORS)
@RolesAllowed({ RolesList.USER, RolesList.ADMIN })
public class SensorsView extends AbstractGridView<Sensor, SensorsForm> {

	private static final long serialVersionUID = 6698245813955647506L;

	@Inject
	private SensorEntityProviderBean entityProvider;

	@Inject
	private SensorsForm form;

	public SensorsView() {
		super(Sensor.class);
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
		return new String[] { "id", "gpioPin", "deviceName" };
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
		rv.put("low", "Минимум (°C)");
		rv.put("top", "Максимум (°C)");
		return rv;
	}

	@Override
	protected EntityProvider<Sensor> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected SensorsForm getEntityForm() {
		return form;
	}

}
