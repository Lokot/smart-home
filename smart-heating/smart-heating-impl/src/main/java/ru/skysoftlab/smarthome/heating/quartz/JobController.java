package ru.skysoftlab.smarthome.heating.quartz;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.apache.openejb.quartz.CronScheduleBuilder;
import org.apache.openejb.quartz.JobBuilder;
import org.apache.openejb.quartz.JobDetail;
import org.apache.openejb.quartz.JobKey;
import org.apache.openejb.quartz.Scheduler;
import org.apache.openejb.quartz.SchedulerException;
import org.apache.openejb.quartz.SimpleScheduleBuilder;
import org.apache.openejb.quartz.Trigger;
import org.apache.openejb.quartz.TriggerBuilder;
import org.apache.openejb.quartz.TriggerKey;
import org.apache.openejb.resource.quartz.QuartzResourceAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.smarthome.heating.annatations.AppProperty;

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

	private Logger LOG = LoggerFactory.getLogger(JobController.class);

	@Resource(lookup = "java:openejb/Resource/QuartzResourceAdapter")
	private QuartzResourceAdapter ra;

	@Inject
	private CdiJobFactory jobFactory;
	
	@Inject
	@AppProperty(INTERVAL)
	// TODO заменить на крон @see SystemConfig
	private Integer interval;

	private Scheduler scheduler;

	@PostConstruct
	public void scheduleJobs() {
		scheduler = ra.getScheduler();
		try {
			scheduler.setJobFactory(jobFactory);
			scheduler.start();
			// JobKey job1Key = JobKey.jobKey("job1", "my-jobs");
			// JobDetail job1 =
			// JobBuilder.newJob(AlarmReadJob.class).withIdentity(job1Key).build();
			//
			// TriggerKey tk1 = TriggerKey.triggerKey("trigger1", "my-jobs");
			// Trigger trigger1 =
			// TriggerBuilder.newTrigger().withIdentity(tk1).startNow()
			// .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5)).build();
			// scheduler.scheduleJob(job1, newHashSet(trigger1), true);
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
			TriggerKey tk1 = TriggerKey.triggerKey("scan-alarm-startUp", SYSTEM_GROUP);
			long now = new Date().getTime();
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tk1).startAt(new Date(now + 10000))
					.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(interval)).build();
			scheduler.scheduleJob(alarmJob, trigger);
		} catch (SchedulerException e) {
			LOG.error("Error create alarm job", e);
		}
	}

	private void startScanTempJob() {
		try {
			JobKey alarmJobKey = JobKey.jobKey("scan-temp", SYSTEM_GROUP);
			JobDetail alarmJob = JobBuilder.newJob(ScanTempJob.class).withIdentity(alarmJobKey).build();
			TriggerKey tk1 = TriggerKey.triggerKey("scan-temp-startUp", SYSTEM_GROUP);
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tk1).startNow()
					.withSchedule(CronScheduleBuilder.cronSchedule("0 0/30 * 1/1 * ? *")).build();
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

}
