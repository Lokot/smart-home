package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import ru.skysoftlab.smarthome.heating.entitys.properties.JpaProperty;
import ru.skysoftlab.smarthome.heating.ui.AbstractForm;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 * Форма ввода свойства.
 * 
 * @author Loktionov Artem
 *
 */
public class ConfigPropertyForm extends AbstractForm<JpaProperty> {

	private static final long serialVersionUID = -9151646347564866108L;

	private TextField id;
	private TextField value;
	private ComboBox propertyType;

	@Override
	protected Collection<? extends Component> getInputs() {
		id = new TextField("Идентификатор");
		value = new TextField("Значение");
		propertyType = new ComboBox("Тип свойства", Arrays.asList(
				Integer.class.getName(), Float.class.getName(),
				Double.class.getName(), Date.class.getName(),
				String.class.getName()));
		Collection<Component> rv = new ArrayList<>();
		rv.add(id);
		rv.add(value);
		rv.add(propertyType);
		return rv;
	}

	@Override
	protected void setFocus() {
		id.focus();
	}

}
