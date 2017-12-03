package ru.skysoftlab.smarthome.heating.services;

import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_MAX_TEMP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_START;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_STOP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.INTERVAL;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.OWFS_SERVER;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.SUMMER_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.TEMP_INTERVAL;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import ru.skysoftlab.skylibs.entitys.properties.api.PropertyProvider;
import ru.skysoftlab.smarthome.heating.dto.SystemConfigDto;

/**
 * Сервис работы с конфигурацией.
 * 
 * @author Артём
 *
 */
public class ConfigService implements Serializable {

	private static final long serialVersionUID = -7765036699971801365L;

	@Inject
	private PropertyProvider propertyProvider;

	@Resource
	private UserTransaction utx;

	public void saveConfig(SystemConfigDto dto) throws Exception {
		try {
			utx.begin();
			propertyProvider.setStringValue(OWFS_SERVER, dto.getUrl(), "Url cервера OWSF");
			propertyProvider.setStringValue(INTERVAL, dto.getAlarmInterval(), "Интервал сканирования сигнализации");
			propertyProvider.setStringValue(TEMP_INTERVAL, dto.getTempInterval(), "Интервал сканирования температур");
			propertyProvider.setBooleanValue(SUMMER_MODE, dto.getSummerMode(), "Режим лето");
			propertyProvider.setBooleanValue(HOLIDAY_MODE, dto.getHoliday(), "Режим отпуск");
			propertyProvider.setDateValue(HOLIDAY_MODE_START, dto.getHolidayStart(), "Начало отпуска");
			propertyProvider.setDateValue(HOLIDAY_MODE_STOP, dto.getHolidayStop(), "Конец отпуска");
			propertyProvider.setFloatValue(HOLIDAY_MODE_MAX_TEMP, dto.getHolidayMaxTemp(), "Максимальная температура");
			utx.commit();
		} finally {
			if (utx.getStatus() == 0) {
				utx.rollback();
			}
		}
	}

}
