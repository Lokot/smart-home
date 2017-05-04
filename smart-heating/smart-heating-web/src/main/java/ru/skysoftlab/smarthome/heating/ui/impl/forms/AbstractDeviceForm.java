package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.impl.GpioController;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.heating.ui.AbstractForm;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

public abstract class AbstractDeviceForm<E extends Device> extends
		AbstractForm<E> {

	private static final long serialVersionUID = 1721334641522446392L;

	@Inject
	protected GpioController gpioController;
	@Inject
	protected SensorsAndDevicesProvider sensorsProvider;

	private ComboBox def;
	private TextField name;
	private CheckBox normaliClosed;

	protected void clearComponents() {
		def.removeAllItems();
	}

	protected void setUpComponents() {
		def.addItems(getFreePins());
	}

	/**
	 * Возвращает список свободных датчиков.
	 * 
	 * @return
	 */
	private Collection<String> getFreePins() {
		Collection<String> rv = gpioController.getPinNames();
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
		normaliClosed = new CheckBox("Нормальнозакрытый");
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
			}
			setFocus();
		}
		setVisible(device != null);
	}

	protected abstract String getDeviceNameTitle();

	protected Collection<Component> getAddonInputs() {
		return new ArrayList<>();
	}

}
