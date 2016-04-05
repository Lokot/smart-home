package ru.skysoftlab.smarthome.heating.quartz;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.openejb.quartz.CronScheduleBuilder;
import org.apache.openejb.quartz.Job;
import org.apache.openejb.quartz.JobBuilder;
import org.apache.openejb.quartz.JobDetail;
import org.apache.openejb.quartz.JobKey;
import org.apache.openejb.quartz.Scheduler;
import org.apache.openejb.quartz.SchedulerException;
import org.apache.openejb.quartz.SimpleScheduleBuilder;
import org.apache.openejb.quartz.SimpleTrigger;
import org.apache.openejb.quartz.Trigger;
import org.apache.openejb.quartz.TriggerBuilder;
import org.apache.openejb.quartz.TriggerKey;
import org.apache.openejb.resource.quartz.QuartzResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.annatations.AppProperty;
import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;

/**
 * Контроллер задач.
 * 
 * @author Локтионов А.Г.
 *
 */
@Startup
@Singleton
public class JobController {

	private static final String SYSTEM_GROUP = "system-jobs";

	public static final String INTERVAL = "owfsScannerInterval";
	public static final String TEMP_INTERVAL = "owfsTempScannerInterval";

	private Logger LOG = LoggerFactory.getLogger(JobController.class);

	private final TriggerKey alarmTK = TriggerKey.triggerKey("scan-alarm-startUp", SYSTEM_GROUP);
	private final TriggerKey tempTK = TriggerKey.triggerKey("scan-temp-startUp", SYSTEM_GROUP);

	@Resource(lookup = "java:openejb/Resource/QuartzResourceAdapter")
	private QuartzResourceAdapter ra;

	@Inject
	private CdiJobFactory jobFactory;

	@Inject
	@AppProperty(INTERVAL)
	private String interval;

	@Inject
	@AppProperty(TEMP_INTERVAL)
	private String tempInterval;

	private Scheduler scheduler;

	@PostConstruct
	public void scheduleJobs() {
		scheduler = ra.getScheduler();
		try {
			scheduler.setJobFactory(jobFactory);
			scheduler.start();
		} catch (SchedulerException e) {
			LOG.error("Error while creating scheduler", e);
		}
		startAlarmReadJob();
		startScanTempJob();
	}

	private void startAlarmReadJob() {
		try {
			JobKey alarmJobKey = JobKey.jobKey("scan-alarm", SYSTEM_GROUP);
			JobDetail alarmJob = JobBuilder.newJob(AlarmReadJob.class).withIdentity(alarmJobKey).build();
			long now = new Date().getTime();
			Trigger trigger = createCronTrigger(alarmTK, interval, new Date(now + 10000));
			scheduler.scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create alarm job", e);
		}
	}

	private void startScanTempJob() {
		try {
			JobKey alarmJobKey = JobKey.jobKey("scan-temp", SYSTEM_GROUP);
			JobDetail alarmJob = JobBuilder.newJob(ScanTempJob.class).withIdentity(alarmJobKey).build();
			Trigger trigger = createCronTrigger(tempTK, tempInterval, null);
			scheduler.scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create scan temp job", e);
		}
	}

	private Set<? extends Trigger> newHashSet(Trigger... trigger) {
		Set<Trigger> set = new HashSet<>();
		for (Trigger t : trigger) {
			set.add(t);
		}
		return set;
	}

	@PreDestroy
	public void stopJobs() {
		if (scheduler != null) {
			try {
				scheduler.shutdown(false);
			} catch (SchedulerException e) {
				LOG.error("Error while closing scheduler", e);
			}
		}
	}

	/**
	 * Слушатель событий.
	 * 
	 * @param event
	 *            событие изменения системных настроек
	 */
	public void editIntervalEvent(@Observes SystemConfigEvent event) {

		String alarmInterval = event.getParam(INTERVAL);
		if (alarmInterval != null && alarmInterval.length() > 0 && !interval.equals(alarmInterval)) {
			interval = alarmInterval;
			try {
				rescheduleJobNow(alarmTK, interval);
				LOG.info("RescheduleAlarmJob");
			} catch (SchedulerException e) {
				LOG.error("Error reschedule scan alarm job", e);
			}
		}

		String tempInterval = event.getParam(TEMP_INTERVAL);
		if (tempInterval != null && tempInterval.length() > 0 && !tempInterval.equals(event.getParam(TEMP_INTERVAL))) {
			this.tempInterval = event.getParam(TEMP_INTERVAL);
			try {
				rescheduleJobNow(tempTK, this.tempInterval);
				LOG.info("RescheduleTempJob");
			} catch (SchedulerException e) {
				LOG.error("Error reschedule scan temp job", e);
			}
		}
	}

	/**
	 * Перезапуск задания.
	 * 
	 * @param key
	 * @param cronString
	 * @throws SchedulerException
	 */
	private void rescheduleJobNow(TriggerKey key, String cronString) throws SchedulerException {
		Trigger trigger = createCronTrigger(key, cronString, null);
		scheduler.rescheduleJob(key, trigger);
		LOG.debug("RescheduleJob " + key + " = " + cronString);
	}

	/**
	 * Создает триггер запуска задания.
	 * 
	 * @param key
	 * @param cronString
	 * @param start
	 * @return
	 */
	private Trigger createCronTrigger(TriggerKey key, String cronString, Date start) {
		TriggerBuilder<Trigger> rv = TriggerBuilder.newTrigger().withIdentity(key);
		if (start == null) {
			rv.startNow();
		} else {
			rv.startAt(start);
		}
		rv.withSchedule(CronScheduleBuilder.cronSchedule(cronString));
		return rv.build();
	}

	/**
	 * Задание на будующее с единичным запуском.
	 * 
	 * @param jobClass
	 * @param jobKey
	 * @param tKey
	 * @param startDate
	 */
	private void createFutureJob(Class<? extends Job> jobClass, String name, String group, Date startDate) {
		try {
			final JobDetail alarmJob = JobBuilder.newJob(jobClass).withIdentity(name, group).build();
			final SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).forJob(alarmJob)
					.startAt(startDate).withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
			scheduler.scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create scan temp job", e);
		}
	}

}
