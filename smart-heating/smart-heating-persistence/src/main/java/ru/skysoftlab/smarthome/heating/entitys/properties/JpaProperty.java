package ru.skysoftlab.smarthome.heating.entitys.properties;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import ru.skysoftlab.smarthome.heating.properties.IDbProperty;

@Entity
public class JpaProperty implements IDbProperty, Serializable {

	private static final long serialVersionUID = -1284259336857932785L;

	@Id
	private String id;
	private String value;
	private String propertyType;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	@Transient
	public Class<?> getType() {
		try {
			return Class.forName(propertyType);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
