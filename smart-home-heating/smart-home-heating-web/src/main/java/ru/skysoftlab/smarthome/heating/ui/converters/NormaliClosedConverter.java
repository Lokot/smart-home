package ru.skysoftlab.smarthome.heating.ui.converters;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import ru.skysoftlab.skylibs.web.dto.VaadinItemDto;
import ru.skysoftlab.smarthome.heating.ui.impl.forms.AbstractDeviceForm;

public class NormaliClosedConverter implements Converter<Object, Boolean> {

	private static final long serialVersionUID = 8173857054552519976L;

	@Override
	public Boolean convertToModel(Object value, Class<? extends Boolean> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return false;
		}
		return ((VaadinItemDto) value).<Boolean>getObj();
	}

	@Override
	public Object convertToPresentation(Boolean value, Class<? extends Object> targetType, Locale locale)
			throws ConversionException {
		if (value) {
			return AbstractDeviceForm.NORMALY_CLOSE_ITEM;
		} else {
			return AbstractDeviceForm.NORMALY_OPEN_ITEM;
		}
	}

	@Override
	public Class<Boolean> getModelType() {
		return Boolean.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}

}
