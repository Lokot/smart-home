package ru.skysoftlab.smarthome.heating.entitys.properties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import ru.skysoftlab.smarthome.heating.entitys.properties.api.ApplicationProperty;

@Entity(name = "booleanProperty")
@Table(name = "boolean_property")
public class BooleanProperty implements ApplicationProperty<Boolean>,
		Serializable {

	private static final long serialVersionUID = 586089832829495884L;

	private String key;

	private Boolean value;

	private String name;

	@Id
	@Length(min = 8, max = 128)
	@Column(name = "key", length = 128, nullable = false)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "value", nullable = true)
	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	@Column(name = "name", nullable = true)
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object value) {
		if (value == this) {
			return true;
		}
		if (!(value instanceof BooleanProperty)) {
			return false;
		}
		BooleanProperty obj = (BooleanProperty) value;
		if (!obj.getKey().equals(getKey())) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 29;
		result += 29 * (getKey() != null ? getKey().hashCode() : 0);
		return result;
	}

}
