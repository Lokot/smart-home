package ru.skysoftlab.smarthome.heating.ui.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class NormaliClosedStringConverter implements Converter<String, Boolean> {

	private static final long serialVersionUID = 8173857054552519976L;

	private final String NORMALY_CLOSE = "Нормально закрыт";
	private final String NORMALY_OPEN = "Нормально открыт";

	@Override
	public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale)
			throws ConversionException {
		if (value.equals(NORMALY_CLOSE)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value) {
			return NORMALY_CLOSE;
		} else {
			return NORMALY_OPEN;
		}
	}

	@Override
	public Class<Boolean> getModelType() {
		return Boolean.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
