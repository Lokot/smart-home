package ru.skysoftlab.smarthome.heating.ui.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import ru.skysoftlab.smarthome.heating.devices.ValveState;

public class ValveStateStringConverter implements Converter<String, ValveState> {

	private static final long serialVersionUID = 8173857054552519976L;

	private final String AUTO_NAME = "Автоматическое управление";
	private final String CLOSE_NAME = "Всегда закрыт";
	private final String OPEN_NAME = "Всегда открыт";

	@Override
	public ValveState convertToModel(String value, Class<? extends ValveState> targetType, Locale locale)
			throws ConversionException {
		if (value.equals(CLOSE_NAME)) {
			return ValveState.CLOSE_ALL;
		} else if (value.equals(OPEN_NAME)) {
			return ValveState.OPEN_ALL;
		} else {
			return ValveState.AUTO;
		}
	}

	@Override
	public String convertToPresentation(ValveState value, Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return "Не установлено";
		}
		switch (value) {
		case CLOSE_ALL:
			return CLOSE_NAME;

		case OPEN_ALL:
			return OPEN_NAME;

		case AUTO:
		default:
			return AUTO_NAME;
		}
	}

	@Override
	public Class<ValveState> getModelType() {
		return ValveState.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
