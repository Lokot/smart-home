package ru.skysoftlab.smarthome.heating.ui.forms;

import java.util.ArrayList;
import java.util.Collection;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;

import ru.skysoftlab.smarthome.heating.entitys.GpioPin;

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class GpioForm extends AbstractForm<GpioPin> {

	private static final long serialVersionUID = 2372643403143137631L;

	private TextField pinId;
	private TextField pinGpio;
	private TextField pinPin;
	private TextField pinDef;
	private TextField pinName;
	private CheckBox pinNormaliClosed;

	@Override
	protected Collection<? extends Component> getInputs() {
		pinId = new TextField("Идентификатор");
		pinGpio = new TextField("Номер GPIO");
		pinPin = new TextField("Номер пина на плате");
		pinDef = new TextField("PC");
		pinName = new TextField("Контур");
		pinNormaliClosed = new CheckBox("Нормальнозакрытый");
		Collection<Component> rv = new ArrayList<>();
		rv.add(pinId);
		rv.add(pinGpio);
		rv.add(pinPin);
		rv.add(pinDef);
		rv.add(pinName);
		rv.add(pinNormaliClosed);
		return rv;
	}

	@Override
	protected void setFocus() {
		pinId.focus();
	}

}
