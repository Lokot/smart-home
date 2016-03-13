package ru.skysoftlab.smarthome.heating.system;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.deltaspike.core.api.config.ConfigResolver;

import ru.skysoftlab.smarthome.heating.entitys.properties.BooleanProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.DateProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.DoubleProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.IntegerProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.LongProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.StringProperty;
import ru.skysoftlab.smarthome.heating.entitys.properties.api.PropertyProvider;

public class PropertyProviderImpl implements PropertyProvider, Serializable {

	private static final long serialVersionUID = 586089832829495884L;

	// private static final Logger log = Logger
	// .getLogger(PropertyProviderImpl.class);

	@Inject
	private EntityManager em;

	public String getStringValue(String key) {
		StringProperty propertyValue = em.find(StringProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(String.class).getValue();
		}
	}

	public Integer getIntegerValue(String key) {
		IntegerProperty propertyValue = em.find(IntegerProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(Integer.class).getValue();
		}
	}

	public Long getLongValue(String key) {
		LongProperty propertyValue = em.find(LongProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(Long.class).getValue();
		}
	}

	public Double getDoubleValue(String key) {
		DoubleProperty propertyValue = em.find(DoubleProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(Double.class).getValue();
		}
	}

	public Boolean getBooleanValue(String key) {
		BooleanProperty propertyValue = em.find(BooleanProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(Boolean.class).getValue();
		}
	}

	public Date getDateValue(String key) {
		DateProperty propertyValue = em.find(DateProperty.class, key);
		if (propertyValue != null) {
			return propertyValue.getValue();
		} else {
			return ConfigResolver.resolve(key).as(Date.class).getValue();
		}
	}

}
