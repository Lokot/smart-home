package ru.skysoftlab.smarthome.heating.entitys.properties;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import ru.skysoftlab.smarthome.heating.entitys.properties.api.ApplicationProperty;

@Entity(name = "stringProperty")
@Table(name = "string_property")
public class StringProperty implements ApplicationProperty<String>, Serializable {

    private static final long serialVersionUID = 586089832829495884L;

    private String key;

    private String value;

    private String name;

    @Id
    @Length(min = 8, max = 128)
    @Column(name = "pkey", length = 128, nullable = false)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Length(max = 256)
    @Column(name = "value", nullable = true)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
        if (!(value instanceof StringProperty)) {
            return false;
        }
        StringProperty obj = (StringProperty) value;
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
