package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.cdi.GpioPinEntityProviderBean;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.ui.AbstractGridView;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.GpioForm;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.cdi.CDIView;
import com.vaadin.data.Container.Indexed;

@CDIView(NavigationService.GPIO)
public class GpioView extends AbstractGridView<GpioPin, GpioForm> {

	private static final long serialVersionUID = 6698245813955647506L;

	@Inject
	private GpioPinEntityProviderBean entityProvider;

	@Inject
	private GpioForm form;

	public GpioView() {
		super(GpioPin.class);
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый пин";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "id", "owner" };
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "gpio", "pin", "def", "name", "normaliClosed",
				"type" };
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

	@Override
	protected String getTitle() {
		return "Список устройств";
	}

	@Override
	protected Map<String, String> getColumnsNames() {
		Map<String, String> rv = new HashMap<>();
		rv.put("gpio", "Виртуальный пин");
		rv.put("pin", "Пин на материнской плате");
		rv.put("def", "Идентификационный номер на плате");
		rv.put("name", "Наименование");
		rv.put("normaliClosed", "Нормально закрытый");
		rv.put("type", "Тип устройства");
		return rv;
	}

	@Override
	protected EntityProvider<GpioPin> getEntityProvider() {
		return entityProvider;
	}

	@Override
	protected GpioForm getEntityForm() {
		return form;
	}

}
