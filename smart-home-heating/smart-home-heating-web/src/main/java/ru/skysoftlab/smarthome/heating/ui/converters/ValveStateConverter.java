package ru.skysoftlab.smarthome.heating.ui.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import ru.skysoftlab.skylibs.web.dto.VaadinItemDto;
import ru.skysoftlab.smarthome.heating.devices.ValveState;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.ValveForm;

public class ValveStateConverter implements Converter<Object, ValveState> {

	private static final long serialVersionUID = 2570832993397857818L;

	@Override
	public ValveState convertToModel(Object value, Class<? extends ValveState> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return ValveState.AUTO;
		}
		return ((VaadinItemDto) value).<ValveState>getObj();
	}

	@Override
	public Object convertToPresentation(ValveState value, Class<? extends Object> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return ValveForm.AUTO_ITEM;
		}
		switch (value) {
		case CLOSE_ALL:
			return ValveForm.CLOSE_ALL_ITEM;
		case OPEN_ALL:
			return ValveForm.OPEN_ALL_ITEM;
		case AUTO:
		default:
			return ValveForm.AUTO_ITEM;
		}
	}

	@Override
	public Class<ValveState> getModelType() {
		return ValveState.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}

}
