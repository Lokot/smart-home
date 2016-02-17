package ru.skysoftlab.smarthome.heating.ui.forms;

import java.util.ArrayList;
import java.util.Collection;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;

import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.Component;
import com.vaadin.ui.Slider;
import com.vaadin.ui.TextField;

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class SensorsForm extends AbstractForm<Sensor> {

	private static final long serialVersionUID = 2372643403143137631L;

	private TextField sensorId;
	private TextField name;
	private Slider maxTemp;

	@Override
	protected Collection<? extends Component> getInputs() {
		sensorId = new TextField("Идентификатор");
		name = new TextField("Помещение");
		maxTemp = new Slider("Максимальная температура (C)", 5, 30);
		maxTemp.setOrientation(SliderOrientation.HORIZONTAL);
		Collection<Component> rv = new ArrayList<>();
		rv.add(sensorId);
		rv.add(name);
		rv.add(maxTemp);
		return rv;
	}

	@Override
	protected void setFocus() {
		sensorId.focus();
	}

}
