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
import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.jpa.BoilerEntityProviderBean;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.BoilerForm;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container.Indexed;

/**
 * Управление нагревателями.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(NavigationService.BOILERS)
@MainMenuItem(name = "Настройки", order = MainMenu.CONFIG)
@MenuItemView(name = "Нагреватели", order = ConfigMenu.BOILERS)
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
	protected String getNewButtonLabel() {
		return "Новый нагреватель";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "sensors", "type" }; // , "SignalOff", "SignalOn"
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "def", "userName", "normaliClosed",
				"type" };
	}

	@Override
	protected String getTitle() {
		return "Список нагревателей";
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("def", "Идентификационный номер на плате");
		rv.put("userName", "Нагреватель");
		rv.put("normaliClosed", "Нормально закрытый");
		return rv;
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}
	
	@Override
	protected EntityProvider<Boiler> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected BoilerForm getEntityForm() {
		return form;
	}

}
