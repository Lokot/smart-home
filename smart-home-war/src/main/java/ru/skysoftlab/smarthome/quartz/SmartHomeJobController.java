package ru.skysoftlab.smarthome.quartz;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import ru.skysoftlab.skylibs.events.ConfigurationListener;
import ru.skysoftlab.skylibs.events.EntityChangeEvent;
import ru.skysoftlab.skylibs.events.EntityChangeListener;
import ru.skysoftlab.skylibs.events.SystemConfigEvent;
import ru.skysoftlab.skylibs.quartz.JobController;
import ru.skysoftlab.smarthome.common.SmartHomeScheduler;

/**
 * Контроллер задач.
 * 
 * @author Локтионов А.Г.
 *
 */
@Startup
@Singleton
@Local({ ConfigurationListener.class, EntityChangeListener.class })
public class SmartHomeJobController extends JobController implements ConfigurationListener, EntityChangeListener {

	@Inject
	@Any
	private Instance<SmartHomeScheduler> instanceScheduler;

	@Override
	public void configUpdated(@Observes SystemConfigEvent event) {
		for (SmartHomeScheduler scheduler : instanceScheduler) {
			scheduler.configUpdated(event);
		}
	}

	@Override
	public void entityChange(@Observes EntityChangeEvent event) {
		for (SmartHomeScheduler scheduler : instanceScheduler) {
			scheduler.entityChange(event);
		}
	}

	@Override
	protected void startJobs() {
		for (SmartHomeScheduler scheduler : instanceScheduler) {
			scheduler.initJobController(this);
		}
	}

}
