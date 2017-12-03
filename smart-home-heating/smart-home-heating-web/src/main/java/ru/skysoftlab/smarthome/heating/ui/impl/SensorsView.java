package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container.Indexed;

import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.skylibs.web.annatations.MainMenuItem;
import ru.skysoftlab.skylibs.web.annatations.MenuItemView;
import ru.skysoftlab.skylibs.web.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.jpa.SensorEntityProviderBean;
import ru.skysoftlab.smarthome.heating.ui.converters.ValvesListStringConverter;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.SensorsForm;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Управление датчиками.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(HeatingMenuNames.SENSORS)
@MainMenuItem(name = "Отопление", order = MainMenuOrder.HEATING_)
@MenuItemView(name = "Датчики", order = HeatingMenuOrder.SENSORS)
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
	protected void configureGrid() {
		grid.getColumn("gpioPin").setConverter(new ValvesListStringConverter());
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "name", "sensorId", "low", "top", "gpioPin" };
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("sensorId", "Идентификатор датчика");
		rv.put("name", "Наименование");
		rv.put("low", "Минимум (°C)");
		rv.put("top", "Максимум (°C)");
		rv.put("gpioPin", "Контуры");
		return rv;
	}

	@Override
	protected String getDelButtonLabel() {
		return "Удалить датчик";
	}

	@Override
	protected SensorsForm getEntityForm() {
		return form;
	}

	@Override
	protected EntityProvider<Sensor> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый датчик";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "deviceName", "master", "id" };
	}

	@Override
	protected String getTitle() {
		return "Список датчиков";
	}

	@Override
	protected Indexed refreshData(String stringFilter) {
		// TODO вставить фильтрацию
		// contactList.setContainerDataSource(new BeanItemContainer<>(
		// Sensor.class, service.findAll(stringFilter)));
		return getJpaContainer();
	}

}
