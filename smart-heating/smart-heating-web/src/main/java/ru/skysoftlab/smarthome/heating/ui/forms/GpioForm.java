package ru.skysoftlab.smarthome.heating.ui.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;

import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class GpioForm extends AbstractForm<GpioPin> {

	private static final long serialVersionUID = 2372643403143137631L;

	private TextField gpio;
	private TextField pin;
	private TextField def;
	private TextField name;
	private CheckBox normaliClosed;
	private ComboBox type;
	
	@Override
	protected Collection<? extends Component> getInputs() {
		gpio = new TextField("Виртуальный пин");
		pin = new TextField("Пин на материнской плате");
		def = new TextField("Идентификационный номер на плате");
		name = new TextField("Контур");
		normaliClosed = new CheckBox("Нормальнозакрытый");
		type = new ComboBox("Тип устройства", EnumSet.allOf(GpioPinType.class));
		Collection<Component> rv = new ArrayList<>();
		rv.add(gpio);
		rv.add(pin);
		rv.add(def);
		rv.add(name);
		rv.add(normaliClosed);
		rv.add(type);
		return rv;
	}

	@Override
	protected void setFocus() {
		gpio.focus();
	}

//	@Override
//	public void init() {
//				
//	}

}
