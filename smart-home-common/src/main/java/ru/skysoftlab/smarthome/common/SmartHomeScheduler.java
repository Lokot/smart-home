package ru.skysoftlab.smarthome.common;

import ru.skysoftlab.skylibs.events.EntityChangeEvent;
import ru.skysoftlab.skylibs.events.SystemConfigEvent;
import ru.skysoftlab.skylibs.quartz.JobController;

/**
 * Интерфейс сервиса управления задачами подсистем.
 * 
 * @author Lokot
 *
 */
public interface SmartHomeScheduler {

	public void configUpdated(SystemConfigEvent event);

	public void entityChange(EntityChangeEvent event);

	/**
	 * Инициализирует задачи в подсистеме.
	 * 
	 * @param scheduler
	 *            планировщик
	 */
	public void initJobController(JobController controller);

}
