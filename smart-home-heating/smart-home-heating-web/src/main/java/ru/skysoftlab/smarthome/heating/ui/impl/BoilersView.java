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
import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.jpa.BoilerEntityProviderBean;
import ru.skysoftlab.smarthome.heating.ui.converters.NormaliClosedStringConverter;
import ru.skysoftlab.smarthome.heating.ui.converters.SensorsListStringConverter;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.BoilerForm;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Управление нагревателями.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(HeatingMenuNames.BOILERS)
@MainMenuItem(name = "Отопление", order = MainMenuOrder.HEATING_)
@MenuItemView(name = "Нагреватели", order = HeatingMenuOrder.BOILERS)
@RolesAllowed({ RolesList.ADMIN })
public class BoilersView extends AbstractGridView<Boiler, BoilerForm> {

	private static final long serialVersionUID = 6698245813955647506L;

	@Inject
	private BoilerEntityProviderBean entityProvider;

	@Inject
	private BoilerForm form;

	public BoilersView() {
		super(Boiler.class);
	}

	@Override
	protected void configureGrid() {
		grid.getColumn("normaliClosed").setConverter(new NormaliClosedStringConverter());
		grid.getColumn("sensors").setConverter(new SensorsListStringConverter());
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "name", "def", "normaliClosed", "sensors" };
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("def", "Пин на плате");
		rv.put("name", "Нагреватель");
		rv.put("normaliClosed", "Тип");
		rv.put("sensors", "Датчики");
		return rv;
	}

	@Override
	protected String getDelButtonLabel() {
		return "Удалить нагреватель";
	}

	@Override
	protected BoilerForm getEntityForm() {
		return form;
	}

	@Override
	protected EntityProvider<Boiler> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый нагреватель";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "type", "id" };
	}

	@Override
	protected String getTitle() {
		return "Список нагревателей";
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

}
