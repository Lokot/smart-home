package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import static ru.skysoftlab.skylibs.web.ui.VaadinUtils.comboboxReadOnlyAndSelectFirst;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;

import ru.skysoftlab.skylibs.web.dto.VaadinItemDto;
import ru.skysoftlab.smarthome.heating.devices.ValveState;
import ru.skysoftlab.smarthome.heating.entitys.Valve;
import ru.skysoftlab.smarthome.heating.ui.converters.ValveStateConverter;

/**
 * Форма устройств.
 * 
 * @author Loktionov Artem
 *
 */
public class ValveForm extends AbstractDeviceForm<Valve> {

	public static final VaadinItemDto AUTO_ITEM = new VaadinItemDto(ValveState.AUTO, "Автоматическое управление");
	public static final VaadinItemDto CLOSE_ALL_ITEM = new VaadinItemDto(ValveState.CLOSE_ALL, "Всегда закрыт");
	public static final VaadinItemDto OPEN_ALL_ITEM = new VaadinItemDto(ValveState.OPEN_ALL, "Всегда открыт");

	private static final long serialVersionUID = 2372643403143137631L;

	private ComboBox valveState;

	@Override
	protected void clearComponents() {
		super.clearComponents();
		valveState.removeAllItems();
	}

	@Override
	protected Collection<Component> getAddonInputs() {
		valveState = new ComboBox("Состояние контура");
		valveState.setConverter(new ValveStateConverter());

		Collection<Component> rv = new ArrayList<>();
		rv.add(valveState);
		return rv;
	}

	@Override
	protected String getDeviceNameTitle() {
		return "Контур";
	}

	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		valveState.addItem(AUTO_ITEM);
		valveState.addItem(OPEN_ALL_ITEM);
		valveState.addItem(CLOSE_ALL_ITEM);
		comboboxReadOnlyAndSelectFirst(valveState);
	}

}
