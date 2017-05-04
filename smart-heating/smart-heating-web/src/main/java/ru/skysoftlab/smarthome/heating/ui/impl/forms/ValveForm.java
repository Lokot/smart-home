package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.entitys.Valve;
import ru.skysoftlab.smarthome.heating.impl.GpioController;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.heating.ui.AbstractForm;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 * Форма устройств.
 * 
 * @author Loktionov Artem
 *
 */
public class ValveForm extends AbstractDeviceForm<Valve> {

	private static final long serialVersionUID = 2372643403143137631L;
//	@Inject
//	private GpioController gpioController;
//	@Inject
//	private SensorsAndDevicesProvider sensorsProvider;
//
//	private ComboBox def;
//	private TextField name;
//	private CheckBox normaliClosed;
//	
//	private void clearComponents() {
//		def.removeAllItems();
//	}
//	
//	private void setUpComponents() {
//		def.addItems(getFreePins());
//	}
//	
//	/**
//	 * Возвращает список свободных датчиков.
//	 * 
//	 * @return
//	 */
//	private Collection<String> getFreePins() {
//		Collection<String> rv = gpioController.getPinNames();
//		Collection<Device> devices = sensorsProvider.getAllDevices();
//		for (Device device : devices) {
//			rv.remove(device.getDef());
//		}
//		return rv;
//	}
//	
//	@Override
//	protected Collection<? extends Component> getInputs() {
//		def = new ComboBox("Пин на плате");
//		name = new TextField("Контур");
//		normaliClosed = new CheckBox("Нормальнозакрытый");
//		Collection<Component> rv = new ArrayList<>();
//		rv.add(def);
//		rv.add(name);
//		rv.add(normaliClosed);
//		return rv;
//	}
//
//	@Override
//	protected void setFocus() {
//		def.focus();
//	}
//
//	@Override
//	public void edit(Valve valve) {
//		this.entity = valve;
//		clearComponents();
//		if (valve != null) {
//			// Bind the properties of the contact POJO to fiels in this form
//			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(valve, this);
//			setUpComponents();
//			if (valve.getDef() != null) {
//				// добавляем редактируемый
//				def.addItem(valve.getDef());
//			}
//			setFocus();
//		}
//		setVisible(valve != null);
//	}

	@Override
	protected String getDeviceNameTitle() {
		return "Контур";
	}

	
}
