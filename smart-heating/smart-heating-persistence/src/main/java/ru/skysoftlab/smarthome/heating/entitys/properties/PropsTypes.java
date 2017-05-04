package ru.skysoftlab.smarthome.heating.entitys.properties;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ru.skysoftlab.smarthome.heating.entitys.properties.api.ApplicationProperty;

/**
 * Перечень типов свойств. 
 * @author a.loktionov
 *
 */
public enum PropsTypes {
	
	BOOLEAN(BooleanProperty.class),
	
	DATE(DateProperty.class),
	
	DOUBLE(DoubleProperty.class),
	
	INTEGER(IntegerProperty.class),
	
	LONG(LongProperty.class),
	
	STRING(StringProperty.class);
	
	/** 
	 * Класс типа свойства. 
	 */
	private Class<? extends ApplicationProperty<?>> clazz;
	
	private PropsTypes(Class<? extends ApplicationProperty<?>> aClazz){
		clazz = aClazz;
	}
	
	/**
	 * Возвращает класс свойства.
	 * @return класс свойства
	 */
	public Class<? extends ApplicationProperty<?>> getTypeClazz() {
		return clazz;
	}
	
	/**
	 * Создает объект заданного типа для свойства.
	 * @param str значение свойства
	 * @return свойство заданного типа
	 * @throws IllegalArgumentException вызывается, если зхначение не соответствует типу данных
	 * @throws ParseException вызывается, если неверный формат даты "yyyy-MM-dd"
	 */
	public Object propFromString(String str) throws IllegalArgumentException, ParseException{
		Object rv = null;
		switch(this){
			case BOOLEAN:
				rv = Boolean.valueOf(str);
				break;
				
			case DATE:
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
				rv = ft.parse(str); 
				break;	
			
			case DOUBLE:
				rv = Double.valueOf(str);
				break;
				
			case INTEGER:
				rv = Integer.valueOf(str);
				break;
				
			case LONG:
				rv = Long.valueOf(str);
				break;
				
			case STRING:
			default:
				rv = str;
				break;
		}
		return rv;
	}
	
}