package ru.skysoftlab.smarthome.heating.system;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import ru.skysoftlab.smarthome.heating.annatations.AppProperty;

public class PropertyProducer implements Serializable {

	private static final long serialVersionUID = 586089832829495884L;
	
	@Produces @AppProperty("")
	public String getStringProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getStringValue(getPropertyName(ip));
	}
	
	@Produces @AppProperty("")
	public UUID getUUIDProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		String uuidStr = provider.getStringValue(getPropertyName(ip));
		if (uuidStr != null) {
			try {
				return UUID.fromString(uuidStr);
			} catch(IllegalArgumentException e) {
				return null;
			}			
		} else {
			return null;	
		}		
	}
	
	@Produces @AppProperty("")
	public File getFileProperty(InjectionPoint ip,  PropertyProviderImpl provider) {
		String path = provider.getStringValue(getPropertyName(ip));
		if (path != null) {
			return new File(path);
		} else {
			return null;	
		}		
	}
	
	@Produces @AppProperty("")
	public Integer getIntegerProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getIntegerValue(getPropertyName(ip));
	}
	
	@Produces @AppProperty("")
	public Float getFloatProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getFloatValue(getPropertyName(ip));
	}
	
	@Produces @AppProperty("")
	public Long getLongProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getLongValue(getPropertyName(ip));
	}
	
	@Produces @AppProperty("")
	public Double getDoubleProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getDoubleValue(getPropertyName(ip));
	}
	
	@Produces @AppProperty("")
	public Boolean getBooleanProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getBooleanValue(getPropertyName(ip));
	}

	@Produces @AppProperty("")
	public Date getDateProperty(InjectionPoint ip,  PropertyProviderImpl provider) {		
		return provider.getDateValue(getPropertyName(ip));
	}	
	
	private String getPropertyName(InjectionPoint ip) {
		return ip.getAnnotated().getAnnotation(AppProperty.class).value();
	}
	
}
