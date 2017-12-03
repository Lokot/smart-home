package ru.skysoftlab.smarthome.heating.toDelete;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.data.Container.Indexed;

import ru.skysoftlab.skylibs.entitys.properties.JpaProperty;
import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.skylibs.web.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.jpa.JpaPropertyEntityProviderBean;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.ConfigPropertyForm;

/**
 * Управление конфигурационными свойствами.
 * 
 * @author Loktionov Artem
 *
 */
// @CDIView(NavigationService.CONFIG)
@RolesAllowed({ RolesList.ADMIN })
public class ConfigProperties extends AbstractGridView<JpaProperty, ConfigPropertyForm> {

	private static final long serialVersionUID = 8056035440071000165L;

	@Inject
	private ConfigPropertyForm configPropertyForm;

	@Inject
	private JpaPropertyEntityProviderBean entityProvider;

	public ConfigProperties() {
		super(JpaProperty.class);
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "id", "value", "propertyType" };
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("id", "Идентификатор");
		rv.put("value", "Значение");
		rv.put("propertyType", "Тип данных");
		return rv;
	}

	@Override
	protected String getDelButtonLabel() {
		return "Удалить свойство";
	}

	@Override
	protected ConfigPropertyForm getEntityForm() {
		return configPropertyForm;
	}

	@Override
	protected EntityProvider<JpaProperty> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новое свойство";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] {};
	}

	@Override
	protected String getTitle() {
		return "Настройки системы:";
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

}
