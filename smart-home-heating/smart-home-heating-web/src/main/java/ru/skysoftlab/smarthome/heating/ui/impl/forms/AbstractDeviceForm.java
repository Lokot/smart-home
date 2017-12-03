package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import static ru.skysoftlab.skylibs.web.ui.VaadinUtils.comboboxReadOnly;
import static ru.skysoftlab.skylibs.web.ui.VaadinUtils.comboboxReadOnlyAndSelectFirst;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.skysoftlab.gpio.DigitalPin;
import ru.skysoftlab.skylibs.web.dto.VaadinItemDto;
import ru.skysoftlab.skylibs.web.ui.AbstractForm;
import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.impl.GpioController;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.heating.ui.converters.NormaliClosedConverter;

public abstract class AbstractDeviceForm<E extends Device> extends AbstractForm<E> {

	public static final VaadinItemDto NORMALY_CLOSE_ITEM = new VaadinItemDto(true, "Нормально закрытый");

	public static final VaadinItemDto NORMALY_OPEN_ITEM = new VaadinItemDto(false, "Нормально открытый");
	private static final long serialVersionUID = 1721334641522446392L;

	private ComboBox def;
	@Inject
	protected GpioController gpioController;

	private TextField name;
	private ComboBox normaliClosed;
	@Inject
	protected SensorsAndDevicesProvider sensorsProvider;

	protected void clearComponents() {
		def.removeAllItems();
		normaliClosed.removeAllItems();
	}

	@Override
	public void edit(E device) {
		this.entity = device;
		clearComponents();
		if (device != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(device, this);
			setUpComponents();
			if (device.getDef() != null) {
				// добавляем редактируемый
				def.addItem(device.getDef());
				def.select(device.getDef());
			}
			setFocus();
		}
		setVisible(device != null);
	}

	protected Collection<Component> getAddonInputs() {
		return new ArrayList<>();
	}

	protected abstract String getDeviceNameTitle();

	/**
	 * Возвращает список свободных датчиков.
	 * 
	 * @return
	 */
	private Collection<DigitalPin> getFreePins() {
		Collection<DigitalPin> rv = gpioController.getPinNames();
		Collection<Device> devices = sensorsProvider.getAllDevices();
		for (Device device : devices) {
			rv.remove(device.getDef());
		}
		return rv;
	}

	@Override
	protected Collection<? extends Component> getInputs() {
		def = new ComboBox("Пин на плате");
		name = new TextField(getDeviceNameTitle());
		normaliClosed = new ComboBox("Тип");
		normaliClosed.setConverter(new NormaliClosedConverter());
		Collection<Component> rv = new ArrayList<>();
		rv.add(def);
		rv.add(name);
		rv.add(normaliClosed);
		rv.addAll(getAddonInputs());
		return rv;
	}

	@Override
	protected void setFocus() {
		def.focus();
	}

	protected void setUpComponents() {
		def.addItems(getFreePins());
		normaliClosed.addItem(NORMALY_OPEN_ITEM);
		normaliClosed.addItem(NORMALY_CLOSE_ITEM);
		comboboxReadOnlyAndSelectFirst(normaliClosed);
		comboboxReadOnly(def);
	}

}
