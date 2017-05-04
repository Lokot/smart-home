package ru.skysoftlab.smarthome.heating.entitys.properties;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import ru.skysoftlab.smarthome.heating.entitys.properties.api.ApplicationProperty;

@Entity(name = "dateProperty")
@Table(name = "date_property")
public class DateProperty implements ApplicationProperty<Date>, Serializable {

    private static final long serialVersionUID = 586089832829495884L;

    private String key;

    private Date value;

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

    @Column(name = "value", nullable = true)
    public Date getValue() {
        return value;
    }

    public void setValue(Date value) {
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
        if (!(value instanceof DateProperty)) {
            return false;
        }
        DateProperty obj = (DateProperty) value;
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
