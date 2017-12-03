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
import ru.skysoftlab.smarthome.heating.entitys.Valve;
import ru.skysoftlab.smarthome.heating.jpa.ValveEntityProviderBean;
import ru.skysoftlab.smarthome.heating.ui.converters.NormaliClosedStringConverter;
import ru.skysoftlab.smarthome.heating.ui.converters.ValveStateStringConverter;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.ValveForm;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Управление устройствами.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(HeatingMenuNames.VALVES)
@MainMenuItem(name = "Отопление", order = MainMenuOrder.HEATING_)
@MenuItemView(name = "Контура", order = HeatingMenuOrder.VALVES)
@RolesAllowed({ RolesList.ADMIN })
public class ValveView extends AbstractGridView<Valve, ValveForm> {

	private static final long serialVersionUID = 6698245813955647506L;

	@Inject
	private ValveEntityProviderBean entityProvider;

	@Inject
	private ValveForm form;

	public ValveView() {
		super(Valve.class);
	}

	@Override
	protected void configureGrid() {
		grid.getColumn("normaliClosed").setConverter(new NormaliClosedStringConverter());
		grid.getColumn("valveState").setConverter(new ValveStateStringConverter());
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "name", "def", "normaliClosed", "valveState" };
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("def", "Пин на плате");
		rv.put("name", "Контур");
		rv.put("normaliClosed", "Тип");
		rv.put("valveState", "Состояние");
		return rv;
	}

	@Override
	protected String getDelButtonLabel() {
		return "Удалить контур";
	}

	@Override
	protected ValveForm getEntityForm() {
		return form;
	}

	@Override
	protected EntityProvider<Valve> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый контур";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "id", "type", "owner" };
	}

	@Override
	protected String getTitle() {
		return "Список контуров отопления";
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

}
