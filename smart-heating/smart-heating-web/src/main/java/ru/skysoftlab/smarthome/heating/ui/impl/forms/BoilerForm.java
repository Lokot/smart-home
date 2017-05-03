package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import java.util.ArrayList;
import java.util.Collection;

import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.ui.AbstractForm;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

/**
 * Форма нагревателя.
 * 
 * @author Loktionov Artem
 *
 */
public class BoilerForm extends AbstractForm<Boiler> {

	private static final long serialVersionUID = 2372643403143137631L;

	private TextField def;
	private TextField name;
	private CheckBox normaliClosed;
	
	@Override
	protected Collection<? extends Component> getInputs() {
		def = new TextField("Идентификационный номер на плате");
		name = new TextField("Нагреватель");
		normaliClosed = new CheckBox("Нормальнозакрытый");
		Collection<Component> rv = new ArrayList<>();
		rv.add(def);
		rv.add(name);
		rv.add(normaliClosed);
		return rv;
	}

	@Override
	protected void setFocus() {
		def.focus();
	}

}
