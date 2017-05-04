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
import ru.skysoftlab.smarthome.heating.entitys.Valve;
import ru.skysoftlab.smarthome.heating.jpa.ValveEntityProviderBean;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.ValveForm;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container.Indexed;

/**
 * Управление устройствами.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(NavigationService.VALVES)
@MainMenuItem(name = "Настройки", order = MainMenu.CONFIG)
@MenuItemView(name = "Контура", order = ConfigMenu.VALVES)
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
	protected String getNewButtonLabel() {
		return "Новый контур";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "type", "owner"};//, "SignalOpen" , "SignalClose"
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "name", "def", "normaliClosed",
				"type" };
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

	@Override
	protected String getTitle() {
		return "Список контуров отопления";
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("def", "Идентификационный номер на плате");
		rv.put("name", "Контур");
		rv.put("normaliClosed", "Нормально закрытый");
		return rv;
	}

	@Override
	protected EntityProvider<Valve> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected ValveForm getEntityForm() {
		return form;
	}

}
