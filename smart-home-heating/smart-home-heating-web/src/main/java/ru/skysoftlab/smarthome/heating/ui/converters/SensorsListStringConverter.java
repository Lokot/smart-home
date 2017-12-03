package ru.skysoftlab.smarthome.heating.ui.converters;

import java.util.Collection;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;

@SuppressWarnings("rawtypes")
public class SensorsListStringConverter implements Converter<String, Collection> {

	private static final long serialVersionUID = 8173857054552519976L;

	@Override
	public Collection convertToModel(String value, Class<? extends Collection> targetType, Locale locale)
			throws ConversionException {
		return null;
	}

	@Override
	public String convertToPresentation(Collection value, Class<? extends String> targetType, Locale locale)
			throws ConversionException {
		if (value == null) {
			return "";
		}
		StringBuilder rv = new StringBuilder();
		for (Object object : value) {
			Sensor sensor = (Sensor) object;
			rv.append(sensor.getName()).append(", ");
		}
		String r = rv.toString();
		return r.substring(0, r.length() - 1);
	}

	@Override
	public Class<Collection> getModelType() {
		return Collection.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
