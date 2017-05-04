package ru.skysoftlab.smarthome.heating.entitys.properties.api;

public interface ApplicationProperty<T> {

    public String getKey();

    public void setKey(String key);

    public T getValue();

    public void setValue(T value);

    public String getName();

    public void setName(String name);
}
